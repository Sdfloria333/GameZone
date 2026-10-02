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

## Adjustment A4 — Stock restoration for returned accessories

### How I used it
- Asked for help understanding why returning an accessory did not restore
  its stock: ReturnService only called ProductService.restoreStock, and
  for accessories that method called AccessoryService.addAccessory, which
  rejects existing IDs and never persists the change.
- Got help diagnosing an IntelliJ build error caused by a JDK path that
  no longer existed.

### Key decisions I made
AccessoryService.restoreStock adds the quantity and saves with
repository.saveAll. Saving is required to persist changes in storage;
without it, stock updates only lived in memory and reverted to the original value after restarting.

ReturnService receives AccessoryService by constructor and decides by
item type: accessories go to AccessoryService, the rest to
ProductService. ReturnService should own the routing logic to avoid hidden dependencies and unnecessary coupling inside ProductService.

ReturnRepository checks AccessoryService first when loading returns.
Making it explicit guarantees accessories are queried directly from their correct repository, preventing lookup errors or unnecessary fallbacks.

I kept the change in ProductService.restoreStock that delegates
accessories to AccessoryService. I kept it as a fallback safeguard in case other components call ProductService directly for accessories.

I left the restored quantity fixed at 1, as in the original code,
because the assignment does not ask to change it. Returning 3 units would still only restore 1 unit, leaving the stock undercounted by 2 units.

### Verification
- The project compiles and runs.
- I sold 1 unit of ACC02, returned it, and the stock went back to 25.
  After restarting the program the stock was still 25.
- Test data JSON files were not committed.