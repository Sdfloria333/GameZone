package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.persons.Person;

import java.util.List;

/**
 * Validates data and business rules related to people (customers and
 * sellers), before a service performs any operation that creates, reads, or
 * modifies a person. This class concentrates validation logic that used to
 * live inline inside {@code PersonService}.
 */
public class PersonValidator {

    private PersonValidator() {
        // Utility class: not meant to be instantiated.
    }

    /**
     * Validates the basic data required to register a person.
     *
     * @param id    the person's identification number, must not be null or blank
     * @param name  the person's name, must not be null or blank
     * @param email the person's email address, must contain "@" and "."
     * @param phone the person's phone number, must not be null or blank
     * @throws InvalidDataException if any of the fields fail validation
     */
    public static void validatePersonData(String id, String name, String email, String phone) {
        if (id == null || id.isBlank()) {
            throw new InvalidDataException("identificación", "no puede estar vacía");
        }
        if (name == null || name.isBlank()) {
            throw new InvalidDataException("nombre", "no puede estar vacío");
        }
        if (phone == null || phone.isBlank()) {
            throw new InvalidDataException("teléfono", "no puede estar vacío");
        }
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new InvalidDataException("email", "debe tener un formato válido");
        }
    }

    /**
     * Validates that a person lookup actually found a person.
     *
     * @param person   the person resolved by the lookup, possibly null
     * @param personId the identifier that was used in the search
     * @throws ResourceNotFoundException if the person is null
     */
    public static void validatePersonExists(Person person, String personId) {
        if (person == null) {
            throw new ResourceNotFoundException("persona", personId);
        }
    }

    /**
     * Validates that no other person with the given identifier already
     * exists in the provided list.
     *
     * @param existing the list of already registered people
     * @param newId    the identifier to check for duplicates
     * @throws BusinessRuleException if a person with that identifier already exists
     */
    public static void validateUniquePerson(List<Person> existing, String newId) {
        if (existing == null || newId == null) {
            return;
        }
        for (Person person : existing) {
            if (newId.equalsIgnoreCase(person.getIdentification())) {
                throw new BusinessRuleException(
                        "Ya existe una persona registrada con la identificación " + newId);
            }
        }
    }
}
