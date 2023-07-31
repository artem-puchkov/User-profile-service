package com.iprody.user.profile.e2e.cucumber.assertion;

import com.iprody.user.profile.e2e.cucumber.JsonSerializationHelper;
import io.cucumber.datatable.DataTable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserDetailsDtoAssertion extends DtoAssertHandler{

    JsonSerializationHelper jsonSerializationHelper;

    @Override
    public void assertResponseBody(DataTable dataTable) {
        var jsonNodeResponseBody = jsonSerializationHelper.getResponseBodyAsJsonNode();
        var expectedDetailsFields = dataTable.asMap();
        var actualDetailsFields = jsonSerializationHelper.detailsJsonToDetailsFieldsMap(jsonNodeResponseBody);
        Assertions.assertThat(actualDetailsFields).containsAllEntriesOf(expectedDetailsFields);
    }
}
