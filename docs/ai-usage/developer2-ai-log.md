# AI Usage Log — Developer 2 (Person Module)

## Tool used
Claude (Anthropic)

## How I used it
- Discussed the design of the Person hierarchy (Person, Customer, Seller):
  why Person should be abstract, what attributes are common vs. specific,
  and why to use an abstract method (getRoleDescription) to enforce
  polymorphism between subclasses.
- Got help understanding and resolving several environment/Git issues:
  a GitHub push permission error (403), configuring my feature branch,
  merging updates from develop, and fixing a broken JDK configuration
  in IntelliJ that was blocking compilation.
- Asked for explanations of Java concepts used in the persistence layer
  (try-with-resources, BufferedReader/FileWriter) to understand how
  file-based persistence works.

## Key decisions I made
- Declared Person as abstract with an abstract method getRoleDescription().
- Split persistence into two files (customers.txt, sellers.txt) instead
  of one combined file, for simpler parsing.
- Decided which fields are common (name, identification, phone) vs.
  specific to each subclass (email; employeeCode and shift).
- Placed the seller preload and customer validation logic in
  PersonService, not in the model classes, to respect the layered
  architecture.

## Adjustment A2 — Circular dependency between SaleService and WarrantyService

### How I used it
- Asked for help understanding why SaleService and WarrantyService formed
  a circular dependency, and why a setter had been used to hide it.
- Got help with Git: leaving the pager (q), committing only code files
  and not test data (JSON files), and syncing my branch with develop
  using merge instead of rebase because the branch was already pushed.
- Asked how to update the class diagram to match the new dependencies.
### Key decisions I made
- WarrantyRepository no longer depends on any service. [a repository should not depend on services.]
- WarrantyService now takes WarrantyRepository, SaleRepository and
  ProductService.
- SaleService receives WarrantyService in its constructor and the field
  is final; I removed setWarrantyService.
- In Main, WarrantyService is built before SaleService.
- I did not commit the JSON files changed by my manual test (consoles,
  customers, sales, warranties) so the PR contains only the fix.

### Verification
- The project compiles in IntelliJ with no errors.
- I registered a sale with a console, the warranty appeared without
  restarting the program, and warranties.json was created.
- SaleService was changed in two commits (one main change, one
  one-line adjustment).
