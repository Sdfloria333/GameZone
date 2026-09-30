# AI Usage Log - Developer 1 (Product Module)

## Session 1: September 5, 2026

Project Development Logbook
1. Git and Repository Setup

At the beginning of the project, I had to learn how to work with the Git repository. I learned how to clone the repository, move between directories and branches, and update my local project with the latest changes.

During this process, I also encountered problems because my project was located inside a OneDrive folder. The synchronization caused conflicts with Git, so I decided to move the project to a local folder.

What I learned: I learned the basic Git workflow and understood the importance of keeping a development project in a suitable local environment.

2. Development Environment Configuration

I had to configure my development environment before starting the implementation. I installed Git and configured the JDK in IntelliJ IDEA so that I could work correctly with the Java project.

At first, I had some difficulties making sure everything was correctly configured, but after checking the necessary tools, I was able to start working on the project.

What I learned: I learned that properly configuring the development environment is an important step before starting the development process.

3. Creating My Feature Branch

Since I was responsible for the Product module, I created a feature branch called feature/product-module.

Working on a separate branch allowed me to make changes without directly affecting the main development branch.

What I learned: I learned how feature branches help organize the work of different developers and reduce conflicts when working as a team.

4. Permission Error

When I tried to work with the repository, I encountered a 403 permission error. At first, I did not understand why I could access the repository but could not perform certain actions.

I discovered that I needed the appropriate permissions to contribute to the repository. After being added as a collaborator, I was able to continue working normally.

What I learned: I learned that having access to a repository does not necessarily mean having permission to modify it.

5. Understanding the Product Structure

While working on the Product module, I analyzed the structure of the classes and the relationships between them.

I understood that Product works as the parent class for more specific classes such as Videogame and Console. This helped me understand how inheritance was being applied in the project.

I also understood why Product needed to be an abstract class. It represents a general product rather than a specific product that should be created directly.

What I learned: I reinforced my understanding of inheritance and abstraction in Java and how these concepts can be used to organize related classes.

6. Understanding Abstract Methods

During the implementation, I had to understand why getDescription() was defined as an abstract method.

I realized that each type of product can have different characteristics and therefore needs its own description. By making the method abstract, each child class is required to provide its own implementation.

What I learned: I learned how abstract methods allow a parent class to define a common behavior while allowing each child class to implement it differently.

7. JavaDoc Documentation

Another important part of the development process was documenting the code using JavaDoc.

I reviewed the documentation requirements and made sure that the necessary classes, constructors, and methods were properly documented.

What I learned: I learned that documentation is an important part of programming because it makes the code easier to understand, maintain, and review.

8. Stock Validation

While working with the product stock functionality, I considered what should happen if a negative value was entered.

I decided that negative stock should not be allowed because it would represent an invalid state in the system. Therefore, I added validation to prevent the stock from becoming negative.

What I learned: I learned the importance of validating information and preventing invalid data from entering the system.

9. Separation of Responsibilities

During the development of the Product module, I had to understand where certain functionalities should be implemented.

One important example was the conversion of products into CSV format. I understood that this functionality belongs to the persistence layer rather than the model because the persistence layer is responsible for storing and retrieving information.

This helped me understand why the different layers of the project should have clearly defined responsibilities.

What I learned: I learned more about separation of responsibilities and the importance of keeping the architecture organized.

10. Correcting a Package Name

During the development process, I noticed that I had incorrectly named a package. I corrected the package name and used git commit --amend to update my previous commit.

This allowed me to correct the mistake without creating an unnecessary additional commit.

What I learned: I learned how to modify the most recent Git commit and the importance of keeping the project's commit history organized.

11. Accidental Modification of a Documentation File

While performing a refactoring operation in IntelliJ IDEA, I noticed that the file docs/analysis.md had been modified even though I did not intend to change it.

After investigating the problem, I realized that the modification was caused by the Rename function used during the refactoring process.

I reviewed the changes and reverted the accidental modification before continuing with my work.

What I learned: I learned that I should always review the changes made by an IDE before committing them, especially after performing refactoring operations.

12. Pull Request and Code Review

Once I finished the changes for my module, I learned how to use the Pull Request workflow.

I pushed my changes to my feature branch and created a Pull Request. Another team member had to review the changes before they could be merged.

I also understood why I could not approve my own Pull Request. The purpose of the review is to have another developer check the changes and identify possible problems.

What I learned: I learned the importance of code review and how Pull Requests help improve the quality of a collaborative project.

13. Updating My Branch After a Merge

After my Pull Request was approved and merged, I learned that I should update my local develop branch before starting new work.

The process consisted of updating develop, removing the old feature branch, and creating a new feature branch based on the updated version.

What I learned: I learned how to maintain an organized Git workflow and ensure that new features are developed using the most recent version of the project.

14. Difference Between git pull and git fetch

During the project, I also learned the difference between git pull and the combination of git fetch and git merge.

I learned that git pull downloads the changes from the remote repository and merges them automatically. On the other hand, git fetch only downloads the changes, allowing me to review them before deciding whether to merge them.

What I learned: I learned that both commands can be used to update a local repository, but git fetch provides more control over the update process.

Final Reflection

Throughout the project, I faced different technical difficulties related to Git, Java, project architecture, and collaborative development.

The development of the Product module allowed me to strengthen my knowledge of object-oriented programming, especially inheritance, abstraction, validation, documentation, and separation of responsibilities.

I also gained more experience with Git and GitHub. I learned how to work with feature branches, solve permission problems, create Pull Requests, participate in code reviews, merge changes, and keep my branches updated.

Overall, this project helped me understand that software development is not only about writing code. It also involves planning, organization, version control, collaboration, documentation, and reviewing the changes made throughout the development process.

## Session 2: Parcial Primer Corte — Requirements 3 (Returns) and 4 (Warranty)

For this exam, the team was reassigned and I was responsible for Requirement 3
(Returns) and Requirement 4 (Warranty) end to end — model, persistence, and
service — plus, later, the integration of Warranty into the sales flow. I used
Claude (Anthropic) throughout.

1. Fixing a Recurring Date Conversion Bug

Early on, `ReturnService.generateMonthlyBalance` failed to compile with
"cannot find symbol: method toInstant()". The code assumed `Sale.getDate()`
returned a `java.util.Date` and tried to convert it, but it already returns
`LocalDate`, which has no `toInstant()` method. The fix was simply to use the
`LocalDate` directly. Weeks later, while merging in code that had been living
on `develop`, I found the exact same bug pattern again, this time inside
`Sale.canBeReturned()`.

What I learned: `toInstant()` only exists on `java.util.Date`, not on
`LocalDate`, and once I recognized the pattern I could spot and fix it
immediately the second time instead of debugging from scratch.

2. Writing the Design Analysis Before Writing Code

For the Warranty requirement, I first asked Claude to answer the five
guiding design questions (`docs/warranty-analysis.md`) using only the exam
statement, before any code existed. Once I uploaded the real project, several
answers turned out to be generic assumptions rather than a match to how the
codebase actually works (for example, the exact signature of
`getAdditionalCost()`, and the persistence format actually used elsewhere in
the project).

What I learned: a design analysis is only useful if it is checked against the
real codebase, not just the spec in isolation — an AI's first pass at a
"why did you design it this way" answer needs to be verified once real code
exists, not accepted at face value.

3. Generating and Verifying Mermaid Class Diagrams

I asked Claude to produce Mermaid class diagrams for the Warranty, Promotion,
and Accessory modules (already merged by teammates) and later a full
project-wide diagram. Rather than accept them as-is, I had Claude re-check
each one against the actual source files. This caught real inaccuracies
carried over from an earlier diagram template — for instance, `Sale` being
shown with a `saleId` field that doesn't exist (the real field is `id`,
exposed through a `getSaleId()` getter), and a method incorrectly attributed
to `Sale` instead of `SaleService`.

What I learned: a generated diagram can look complete and still be wrong in
small but meaningful ways; verifying it line by line against the source is
part of the job, not optional polish.

4. Discovering My Feature Branch Had Fallen Behind

`feature/warranty-module` had been created before the Accessory, Promotion,
and Return modules were merged into `develop`. By the time I went to merge
`develop` back in, my branch was missing dozens of commits it should have had
all along.

What I learned: a feature branch should be kept in sync with `develop`
periodically during development, not only right before opening the PR —
the longer two branches diverge, the larger and riskier the eventual merge
becomes.

5. Diagnosing a Pre-Existing Broken Merge on Develop

While resolving that merge, I found that `Main.java`, `Sale.java`, and
`ConsoleUI.java` on `develop` itself already contained leftover, partially
resolved conflict markers from an earlier, unrelated pull request — missing
closing braces, a duplicated block, and stray branch-name text sitting in the
middle of real code. Git did not flag these as conflicts during my merge,
because from its point of view the previous merge had already been
"resolved" (incorrectly) and committed.

What I learned: the absence of a `CONFLICT` message from Git does not mean
the resulting code is correct — a bad manual conflict resolution can be
committed cleanly and then silently break the build for everyone downstream.
Compiling and testing after a merge is not optional, even when Git reports
success.

6. Recovering from a Messy Local Git State

Partway through, I ended up committing changes to `develop` instead of my
feature branch, and later hit a `merge --abort` that Git refused to run. I
learned to create a throwaway backup branch before doing anything risky, use
`git reset --hard` to discard a broken working state without losing commits
that were already made elsewhere, and re-create a branch cleanly from
`origin` as a known-good source of truth instead of trying to manually patch
a confusing local state.

What I learned: when a git state gets confusing, the safest path is usually
to stop guessing, make a backup pointer, and rebuild from what is on the
remote — not to keep layering more commands on top of an already-uncertain
state.

7. Recognizing (and Justifying an Exception to) a Java Anti-Pattern

`Warranty`'s constructor computes `endDate` by calling the abstract method
`getDurationInMonths()`. Calling an overridable method from a constructor is
a known risk in Java, because a subclass's own fields may not be initialized
yet when the base constructor runs. I confirmed this was safe here
specifically because `getDurationInMonths()` only returns a hardcoded
constant (6 or 12) and never touches any subclass field.

What I learned: it's not enough to know a rule of thumb exists — being able
to check whether it actually applies to a specific case is what lets you
follow the spec's exact requirement (compute the date in the constructor)
without introducing a real bug.

8. Resolving a Circular Dependency at Integration Time

When I finally wired Warranty into the sales flow, `WarrantyRepository`
needed `SaleService` to resolve sale references when loading from disk, and
`SaleService` needed `WarrantyService` to generate warranties during
`registerSale` — a circular dependency if both were wired through
constructors. I resolved it by injecting `WarrantyService` into
`SaleService` through a setter, called once in `Main` after both objects
already exist, instead of through the constructor.

What I learned: setter injection is a practical way to break a circular
dependency between two services when constructor injection would require
each one to exist before the other.

9. Writing Project-Wide Documentation, Not Just Per-Module Docs

Beyond the module-specific analysis and diagrams, I had a project-wide
`README.md`, an updated inheritance hierarchy diagram, a layer diagram, and a
single full-project class diagram generated and cross-checked against
`develop`. For the layer diagram, I specifically asked for the
not-yet-existing connection between `SaleService`/`ConsoleUI` and
`WarrantyService` to be drawn as a dotted line rather than left out, so the
diagram stayed honest about what was and wasn't implemented yet.

What I learned: documentation that quietly omits an unfinished piece is
misleading in a different way than documentation that is simply wrong;
marking something as "not yet connected" is more useful than pretending it
isn't part of the design at all.

10. Completing the Integration and Testing It End to End

Once the model, persistence, and service layers for Warranty existed, I had
Claude implement the actual integration: the extra parameter on
`registerSale` for extended warranty selection, the automatic basic warranty
for consoles, the new menu section in `ConsoleUI`, and the wiring in `Main`.
Before considering it finished, I had it compiled with `javac` and actually
run against the console menu with real input, confirming a console sale
produced both a basic and an extended warranty record, that the receipt
showed the extra cost, and that the records persisted correctly across a
restart.

What I learned: getting code to compile is not the same as verifying it
works — running the actual scenario end to end caught details (like how the
receipt should display the warranty cost) that a purely static review would
have missed.

Final Reflection

This part of the project pushed me further into the messier, real-world side
of collaborative development: branches that fall behind, merges that look
resolved but aren't, and the judgment calls needed to safely recover without
losing work. Compared to the Product module in Taller 1, this stage
reinforced that design documentation and diagrams are only trustworthy once
verified against the actual code, that Git problems are usually recoverable
if you slow down and use a backup branch, and that finishing a feature means
testing it running, not just watching it compile.