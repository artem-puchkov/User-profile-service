package com.iprody.user.profile.service;

import com.iprody.user.profile.entity.User;
import com.iprody.user.profile.entity.UserDetails;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMergeUtils {

    /**
     * @param to retrieved from DB to for update
     * @param from to with updated fields for merging into to
     * @return merged to ready for saving by repository
     */
    public User merge(User to, User from) {
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setEmail(from.getEmail());
        to.setUserDetails(merge(to.getUserDetails(), from.getUserDetails()));
        return to;
    }

    /**
     * @param to   retrieved from DB userDetails for update
     * @param from userDetails with updated fields for merging into 'to'
     * @return merged userDetails ready for saving by repository
     */
    public UserDetails merge(UserDetails to, UserDetails from) {
        to.setTelegramId(from.getTelegramId());
        to.setMobilePhone(from.getMobilePhone());
        return to;
    }
}
