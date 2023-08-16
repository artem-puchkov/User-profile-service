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
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.iprody.user.profile.e2e.cucumber.FieldNamesConst.ERROR_DETAILS_NAME;
import static com.iprody.user.profile.e2e.cucumber.FieldNamesConst.ERROR_DETAILS_SEPARATOR;
import static com.iprody.user.profile.e2e.cucumber.FieldNamesConst.USER_DETAILS_FIELD_NAME;
import static com.iprody.user.profile.e2e.cucumber.FieldNamesConst.USER_DETAILS_FIELD_SEPARATOR;

/**
 * Service class for mapping data set in feature file to model.
 */
@UtilityClass
public class UserProfileApiModelMapper {
    /**
     * Convert Datatable from feature file to UserDto.
     *
     * @param dataTable - userDto parameters in feature file
     * @return UserDto with datatable parameters
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

    public UserDto toUserDto(DataTable dataTable, Consumer<UserDto> modifyFields) {
        final UserDto userDto = toUserDto(dataTable);
        modifyFields.accept(userDto);
        return userDto;
    }

    /**
     * Convert Datatable from feature file to UserDetailsDto.
     *
     * @param dataTable - userDetailsDto parameters in feature file
     * @return UserDetailsDto with datatable parameters
     */
    public UserDetailsDto toUserDetailsDto(DataTable dataTable) {
        final var entry = dataTable.asMap();
        return UserDetailsDto.builder()
                .id(entry.get("id") == null ? null : Long.valueOf(entry.get("id")))
                .userId(entry.get("userId") == null ? null : Long.valueOf(entry.get("userId")))
                .telegramId(entry.get("telegramId"))
                .mobilePhone(entry.get("mobilePhone"))
                .build();
    }

    public UserDetailsDto toUserDetailsDto(DataTable dataTable, Consumer<UserDetailsDto> modifyFields) {
        final var userDetailsDto = toUserDetailsDto(dataTable);
        modifyFields.accept(userDetailsDto);
        return userDetailsDto;
    }

    public Map<String, String> dataTableToUserFieldsMap(DataTable dataTable) {
        return dataTable.asMap().entrySet().stream()
                .filter(e -> !e.getKey().toLowerCase().startsWith(USER_DETAILS_FIELD_NAME) && e.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Converts Datatable from feature file to Map with UserDetails fields for compare by json response.
     * Method takes a table, filters from it only the filled values related to UserDetails and returns them as a Map:
     * Key = value from table after "USER_DETAILS_FIELD_SEPARATOR"
     *
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
        return map.entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase(ERROR_DETAILS_NAME))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Converts Datatable from feature file to List with UserDetails fields for compare by json response.
     * Method takes a table, filters from it String field "details"
     * and converts a string into a collection using a constant separator.
     * Method allows the use of spaces before and after the separator in the feature file (regex \\s* before and after separator)
     *
     * @param dataTable - userDto parameters in feature file
     * @return list with fields
     */
    public List<String> dataTableToErrorDetailsList(DataTable dataTable) {
        var map = dataTable.asMap();
        return Arrays.stream(map.get("details").split("\\s*" + ERROR_DETAILS_SEPARATOR + "\\s*")).toList();
    }
}
