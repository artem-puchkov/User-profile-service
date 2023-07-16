package com.iprody.user.profile.e2e.cucumber;

import com.iprody.user.profile.e2e.generated.model.UserDetailsDto;
import com.iprody.user.profile.e2e.generated.model.UserDto;
import io.cucumber.datatable.DataTable;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.iprody.user.profile.e2e.cucumber.FieldNamesConst.*;


/**
 * Service class for mapping data set in feature file to model.
 */
@UtilityClass
public class UserProfileApiModelMapper {

    /**
     * Convert Datateble from feature file to UserDto.
     * @param dataTable - userDto parameters in feature file
     * @return UserDto with datateble parameters
     */
    public UserDto toUserDto(DataTable dataTable) {
        final var entry = dataTable.asMap();
        return UserDto.builder()
                .id(entry.get("id") == null ? null : Long.valueOf(entry.get("id")))
                .firstName(entry.get("firstName"))
                .lastName(entry.get("lastName"))
                .email(entry.get("email"))
                .userDetailsDto(UserDetailsDto.builder()
                        .id(entry.get("userDetails.id") == null ? null : Long.valueOf(entry.get("userDetails.id")))
                        .mobilePhone(entry.get("userDetails.mobilePhone"))
                        .telegramId(entry.get("userDetails.telegramId"))
                        .userId(entry.get("userDetails.userId") == null ? null : Long.valueOf(entry.get("userDetails.userId")))
                        .build())
                .build();
    }

    public Map<String, String> dataTableToUserFieldsMap(DataTable dataTable) {
        return dataTable.asMap().entrySet().stream()
                .filter(e -> !e.getKey().toLowerCase().startsWith(USER_DETAILS_FIELD_NAME) && e.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Converts Datateble from feature file to Map with UserDetails fields for compare by json response.
     * Method takes a table, filters from it only the filled values related to UserDetails and returns them as a Map:
     * Key = valeu from table after "USER_DETAILS_FIELD_SEPARATOR"
     * @param dataTable - userDto parameters in feature file
     * @return Map with fields
     */
    public Map<String, String> dataTableToDetailsFieldsMap(DataTable dataTable) {
        return dataTable.asMap().entrySet().stream()
                .filter(e -> e.getKey().toLowerCase().startsWith(USER_DETAILS_FIELD_NAME) && e.getValue() != null)
                .map(e -> Map.of(StringUtils.substringAfter(e.getKey(), USER_DETAILS_FIELD_SEPARATOR), e.getValue()))
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Object> dataTableToErrorFieldsMap(DataTable dataTable) {
        var map = dataTable.asMap();
        Map<String, Object> errorFields = map.entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase(ERROR_DETAILS_NAME))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return errorFields;
    }

    /**
     * Converts Datateble from feature file to List with UserDetails fields for compare by json response.
     * Method takes a table, filters from it String field "details"
     * and converts a string into a collection using a constant separator.
     * Method allows the use of spaces before and after the separator in the feature file (regex \\s* before and after separator)
     * @param dataTable - userDto parameters in feature file
     * @return Map with fields
     */
    public List<String> dataTableToErrorDetailsList(DataTable dataTable) {
        var map = dataTable.asMap();
        return Arrays.stream(map.get("details").split("\\s*" + ERROR_DETAILS_SEPARATOR + "\\s*")).toList();
    }
}