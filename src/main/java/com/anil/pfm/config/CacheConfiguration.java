package com.anil.pfm.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.Bank.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.Person.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.BankAccount.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.tx.domain.MyAccount.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.tx.domain.Transaction.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.TransactionCategory.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.TransactionType.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.ExpenseCategory.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.ExpenseCategory.class.getName() + ".subCategories", jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.IncomeCategory.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.IncomeCategory.class.getName() + ".subCategories", jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.Goal.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.mf.domain.MFRTAgent.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.mf.domain.MFCategory.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.mf.domain.AMC.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.mf.domain.MutualFund.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.mf.domain.MFPortfolio.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.mf.domain.MFInvestment.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.FixedDeposit.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.RecurringDeposit.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.RDTransaction.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.PPFAccount.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.PPFTransaction.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.LifeInsuranceCompany.class.getName(), jcacheConfiguration);
            cm.createCache(com.anil.pfm.domain.LifeInsurancePolicy.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
