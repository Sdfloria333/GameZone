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

