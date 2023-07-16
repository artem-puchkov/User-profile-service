package com.iprody.user.profile.e2e.cucumber.assertion;

import com.iprody.user.profile.e2e.cucumber.JsonSerializationHelper;
import com.iprody.user.profile.e2e.cucumber.UserProfileApiModelMapper;
import io.cucumber.datatable.DataTable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.SoftAssertions;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserDtoAssertion extends DtoAssertHandler{

    JsonSerializationHelper jsonSerializationHelper;

    @Override
    public void assertResponseBody(DataTable dataTable) {
        var jsonNodeResponseBody = jsonSerializationHelper.getResponseBodyAsJsonNode();
        var expectedUserFields = UserProfileApiModelMapper.dataTableToUserFieldsMap(dataTable);
        var expectedDetailsFields = UserProfileApiModelMapper.dataTableToDetailsFieldsMap(dataTable);
        var actualUserFields = jsonSerializationHelper.jsonNodeToUserFieldsMap(jsonNodeResponseBody);
        var actualDetailsFields = jsonSerializationHelper.jsonNodeToDetailsFieldsMap(jsonNodeResponseBody);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualUserFields).containsAllEntriesOf(expectedUserFields);
        softly.assertThat(actualDetailsFields).containsAllEntriesOf(expectedDetailsFields);
        softly.assertAll();
    }
}
