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