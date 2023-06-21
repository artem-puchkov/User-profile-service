package com.iprody.user.profile.service.mapper.util;

import com.iprody.user.profile.entity.UserDetails;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserDetailsUtils {

    /**
     * @param detailsTo   retrieved from DB userDetails for update
     * @param detailsFrom userDetails with updated fields for merging into 'detailsTo'
     * @return merged userDetails ready for saving by repository
     */
    public UserDetails merge(UserDetails detailsTo, UserDetails detailsFrom) {
        detailsTo.setTelegramId(detailsFrom.getTelegramId());
        detailsTo.setMobilePhone(detailsFrom.getMobilePhone());
        return detailsTo;
    }
}
