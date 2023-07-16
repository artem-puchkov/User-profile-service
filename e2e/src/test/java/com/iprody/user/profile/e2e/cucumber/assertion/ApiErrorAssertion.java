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
public class ApiErrorAssertion extends DtoAssertHandler {

    JsonSerializationHelper jsonSerializationHelper;

    @Override
    public void assertResponseBody(DataTable dataTable) {
        var jsonNodeResponse = jsonSerializationHelper.getResponseBodyAsJsonNode();
        var expectedFields = UserProfileApiModelMapper.dataTableToErrorFieldsMap(dataTable);
        var actualFields = jsonSerializationHelper.jsonNodeToErrorFieldsMap(jsonNodeResponse);
        var expectedDetails = UserProfileApiModelMapper.dataTableToErrorDetailsList(dataTable);
        var actualDetails = jsonSerializationHelper.jsonNodeToErrorDetailsList(jsonNodeResponse);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualFields).containsAllEntriesOf(expectedFields);
        softly.assertThat(actualDetails).containsAll(expectedDetails);
        softly.assertAll();
    }
}
