package com.oner365.audit.config;

import java.util.Optional;

import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.oner365.data.web.utils.RequestUtils;

/**
 * 审计功能 - 操作人
 *
 * @author zhaoyong
 */
@Component
@Validated
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        if (RequestUtils.getAuthUser() == null) {
            return Optional.empty();
        }
        String userName = RequestUtils.getAuthUser().getUserName();
        return Optional.of(userName);
    }

}
