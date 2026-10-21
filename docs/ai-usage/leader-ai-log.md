# AI Usage Log - GameZone Unicesar

This document records the prompt engineering, architectural assistance, code generation, and verification process associated with Artificial Intelligence tools used during the development of the Sales Module and Tech Lead integration tasks.

---

## 1. Tool Declaration

* **Primary AI Tool:** Gemini (Google DeepMind)
* **Scope of Usage:** Architecture design verification, persistence strategy formulation (JSON with Gson), domain model creation, service layer logic validation, and console UI menu implementation.

---

## 2. Prompts and Interaction Log

### Entry 1: Architecture and Multi-Layer Modeling
* **Prompt Intent:** Define the multi-layer structure (Model, Persistence, Service, UI) for the Sales module and verify integration requirements with existing modules.
* **AI Output:** Structural guidance separating `Sale` and `SaleDetail` entities from file storage and business validation logic.
* **Applied Code:** Created `com.gamezone.model.Sale` and `com.gamezone.model.SaleDetail`.

### Entry 2: Persistence Layer with JSON (Gson)
* **Prompt Intent:** Implement repository logic storing sales in JSON format using Google's Gson library.
* **AI Output:** Implementation of `SaleRepository.java` using `GsonBuilder`, `TypeToken`, and `FileReader`/`FileWriter` streams.
* **Applied Code:** Created `com.gamezone.persistence.SaleRepository`.

### Entry 3: Business Logic and Stock Integration
* **Prompt Intent:** Build `SaleService` enforcing business constraints (minimum one item, inventory validation via `ProductService`, and stock deduction).
* **AI Output:** Exception-driven validation logic connecting the Sales module to product and person dependencies.
* **Applied Code:** Created `com.gamezone.service.SaleService`.

### Entry 4: Interface Wiring and Console Execution
* **Prompt Intent:** Create the 10-option interactive menu in `ConsoleUI` and initialize dependency injection in `Main`.
* **AI Output:** Console menu loop reading terminal input via `Scanner` and wiring option triggers directly to the service layer.
* **Applied Code:** Updated `com.gamezone.ui.ConsoleUI` and `com.gamezone.Main`.

### Entry 5: Requirement Analysis and Tech Lead Task Breakdown (Requirement 6)
* **Date:** 2026-10-21
* **Tool:** Claude
* **Prompt Intent:** Understand the Requirement 6 assignment (custom exceptions and validators) and get a step-by-step guide for the Tech Lead tasks, first from a pasted summary and then from the full requirement PDF.
* **AI Output:** Explanation of the problem context, the split of responsibilities between Developer 1, Developer 2 and the Tech Lead, and the deliverables, including `docs/exception-analysis.md` and `docs/exception-class-diagram.md`.
* **Decision:** Used it as a work-order checklist. [Confirm: I read the PDF myself and checked the explanation against it.]

### Entry 6: Exception Handling Concepts
* **Date:** 2026-10-21
* **Tool:** Claude
* **Prompt Intent:** Explain from scratch how exceptions, `try/catch` and error propagation between layers work (repository → service → UI).
* **AI Output:** Conceptual explanation and a table of the four custom exceptions with examples.
* **Applied Code:** None (learning step before modifying `ConsoleUI`).

### Entry 7: Git Flow Procedure for the Tech Lead
* **Date:** 2026-10-21
* **Tool:** Claude
* **Prompt Intent:** Detailed procedure to create `feature/exception-handling` from `develop`, make atomic commits, review teammates' PRs, integrate, and clean up branches.
* **AI Output:** Ordered command sequence, PR review checklist, and a proposal to use one sub-branch per team member so that three PRs are possible.
* **Decision:** [Confirm: which branch strategy the team actually used, and whether it was checked with the instructor.]

### Entry 8: Review of `ConsoleUI.java` for Exception Handling
* **Date:** 2026-10-21
* **Tool:** Claude
* **Prompt Intent:** Review my complete `ConsoleUI.java` and indicate which `catch` blocks to keep, change or remove.
* **AI Output:** Analysis of the generic `catch (Exception e)` blocks that would hide the four custom exceptions, with proposed changes in `showPromotionsMenu`, `registerSale`, `registerAccessory` and `registerReturn`; a fallback handler for `start()`; and a recommendation for Developer 2 not to make `findProductById` / `findSaleById` throw, because the UI relies on `null` returns.
* **Applied Code:** The four `catch` blocks and imports in `ConsoleUI.start()` were already in place. [Confirm which of the proposed changes you applied and which you discarded, and why.]
* **Commit related:** [your commit messages here]

### Entry 9: README Section Draft
* **Date:** 2026-10-21
* **Tool:** Claude
* **Prompt Intent:** Draft the README section describing the validation and exception layer.
* **AI Output:** A Spanish draft section (components, error flow, user messages).
* **Decision:** [Confirm: you compared it with Developer 2's real code and corrected anything that did not match.]
* **Commit related:** `docs: describe validation and exception layer in README`
---

## 3. Human Code Verification and Adaptation

All AI-generated code was reviewed, validated, and modified manually to meet specific project constraints:

1. **Dependency Management:** Configured Maven dependencies (`pom.xml`) manually to include Google Gson `2.10.1`.
2. **Method Wiring:** Adjusted method invocations inside `ConsoleUI.java` to ensure parameters matched the actual signatures of `ProductService` and `PersonService`.
3. **Error Handling:** Enhanced exception catching (`try-catch` blocks for `NumberFormatException`) to prevent terminal crashes during menu interaction.
4. **Requirement 6 - Adaptation to the real code:** The AI used the class name `ConsoleMenu` from the assignment; the project class is `ConsoleUI`, so all suggestions were adapted to the real code.
5. **Requirement 6 - Verification:** [Confirm: compiled after each change / ran the menu options / tested each of the four error messages / reviewed the PRs of Developer 1 and Developer 2 on GitHub.]
6. **Requirement 6 - Limits of the AI output:** The first answers were generic templates because the AI had not seen the code. Concrete changes came only after sharing `ConsoleUI.java`. The AI did not run or test the code; functional validation was done by me.
### Entrada de Bitácora - Ajuste A3 (Líder Técnico)

* **Fecha:** 2026-09-30
* **Herramienta:** Gemini
* **Fase y Rama:** Fase 3 / `refactor/unified-sale-registration`
* **Objetivo:** Reorganizar el flujo del método `registerSale` en `SaleService` para cumplir con el orden estricto de integración (A3).
* **Consulta:** "explicame mas facil porfavor te voy a pasar mi servicio sale que creo que es donde tengo que hacer todo [código enviado]"
* **Respuesta:** Se identificó que la reducción de inventario se ejecutaba antes de aplicar las promociones y garantías. Se reordenó la lógica para mover la reducción de stock al final, previo a la persistencia en el repositorio.
* **Decisión:** Se aceptó el cambio propuesto para mover `reduceStock` y `updateStock` al paso 7 de la secuencia de ejecución.
* **Commit relacionado:** `refactor: reorder sale registration process to match integration requirements`