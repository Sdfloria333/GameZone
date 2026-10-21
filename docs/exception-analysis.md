# Exception Handling Analysis — Requerimiento 6

This document records the team's discussion before implementing the new exception
and validation layer for GameZone Unicesar. It answers the five orienting questions
from the requirement, explaining the reasoning behind the design decisions we made.

## 1. Should `GameZoneException` be checked or unchecked?

We decided to make `GameZoneException` extend `RuntimeException`, making it
unchecked. Business errors like a product not existing or a stock being insufficient
aren't conditions every caller should be forced to handle explicitly — they're closer
to "something went wrong with this specific operation" than to a recoverable
condition the caller must plan around.

The practical consequence is on method signatures: if `GameZoneException` were
checked, every method in `ProductService`, `PersonService`, `SaleService`, the
repositories, and `ConsoleMenu` would need a `throws` clause, and that `throws`
would have to propagate all the way up through every layer that calls them. That
contradicts one of the requirement's own constraints — preserving the existing
behavior of the system — since it would force us to touch method signatures we
aren't supposed to change. With an unchecked exception, the signatures stay exactly
as they are, and only `ConsoleMenu`, the one place that actually needs to react
differently depending on the error, is responsible for catching it.

## 2. Why a hierarchy with a common root instead of always throwing `IllegalArgumentException`?

A single generic exception type can only communicate that *something* went wrong —
not what kind of problem it was. The caller is left guessing based on the message
text, which is fragile and breaks the moment someone rewords a message. A typed
hierarchy lets each layer throw the exception that actually describes what happened,
and the caller decides how to react based on the exception's type instead of parsing
a string.

This follows the Single Responsibility Principle at the exception level: each
exception class represents exactly one category of failure. It's also the same
polymorphism-based design we already used for `Promotion` and `Warranty` — the
caller works against the common supertype (`GameZoneException`) when it doesn't
need to distinguish, and against the specific subtype when it does.

## 3. Where in the flow are the validators invoked, and can they access persistence?

Validators are invoked from the service layer, at the start of each business
operation, before the operation touches the repository or modifies any model
object. That matches how we've handled business rules throughout the project —
services decide what's valid, not the model or the repository.

This placement keeps the layered architecture intact: validators sit logically
between `service` and `model`. They are not a service and not a model class, just
a focused utility the service consults. Validators must not reach into
`com.gamezone.persistence` — the requirement states this explicitly, and it also
makes sense architecturally: if a validator needs data that's only available through
a repository (like the existing list of people for `validateUniquePerson`), the
service is the one that fetches it and passes it in as a parameter. The validator
never goes looking for it on its own.

## 4. How do we guarantee the existing behavior is preserved, and what evidence shows it?

The refactor is additive in terms of behavior: the same conditions that used to
throw `IllegalArgumentException` still throw an exception under the same
circumstances — only the exception's type changes, from generic to specific. No
validation rule becomes stricter or looser.

To verify this, we ran all 10 existing menu operations before and after the
refactor, covering at least one case per new exception type (a missing resource, a
violated business rule, invalid input data, and a simulated persistence failure),
and compared the user-visible outcome in each case. The only difference is the
wording and clarity of the error message shown to the user — the operations
themselves succeed or fail under exactly the same conditions as before.

## 5. How does `ConsoleMenu` catch the four exception types?

We use a single `try` block with four separate `catch` clauses, one per exception
subtype (`ResourceNotFoundException`, `BusinessRuleException`,
`InvalidDataException`, `PersistenceException`). The four subclasses all extend
`GameZoneException` directly and don't have an inheritance relationship with each
other, so there's no real hierarchy to cascade through — a cascading catch doesn't
apply here.

A single `catch (GameZoneException e)` would compile and technically catch
everything, but it would defeat the whole purpose of the requirement: we'd be back
to one generic message for every kind of error. Multiple specific `catch` blocks are
what let us show a distinct, meaningful message for each category, which is exactly
what the requirement asks for.