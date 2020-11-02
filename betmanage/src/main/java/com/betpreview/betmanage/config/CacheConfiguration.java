package com.betpreview.betmanage.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.betpreview.betmanage.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.betpreview.betmanage.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.betpreview.betmanage.domain.User.class.getName());
            createCache(cm, com.betpreview.betmanage.domain.Authority.class.getName());
            createCache(cm, com.betpreview.betmanage.domain.User.class.getName() + ".authorities");
            createCache(cm, com.betpreview.betmanage.domain.Competition.class.getName());
            createCache(cm, com.betpreview.betmanage.domain.Competition.class.getName() + ".teams");
            createCache(cm, com.betpreview.betmanage.domain.Country.class.getName());
            createCache(cm, com.betpreview.betmanage.domain.MatchPreview.class.getName());
            createCache(cm, com.betpreview.betmanage.domain.MatchPreview.class.getName() + ".titles");
            createCache(cm, com.betpreview.betmanage.domain.MatchPreview.class.getName() + ".paragraphs");
            createCache(cm, com.betpreview.betmanage.domain.MatchPreview.class.getName() + ".teams");
            createCache(cm, com.betpreview.betmanage.domain.Paragraphs.class.getName());
            createCache(cm, com.betpreview.betmanage.domain.SocialMedia.class.getName());
            createCache(cm, com.betpreview.betmanage.domain.Sport.class.getName());
            createCache(cm, com.betpreview.betmanage.domain.Sport.class.getName() + ".competitions");
            createCache(cm, com.betpreview.betmanage.domain.Team.class.getName());
            createCache(cm, com.betpreview.betmanage.domain.Team.class.getName() + ".socialMedias");
            createCache(cm, com.betpreview.betmanage.domain.Team.class.getName() + ".matchPreviews");
            createCache(cm, com.betpreview.betmanage.domain.Title.class.getName());
            createCache(cm, com.betpreview.betmanage.domain.Country.class.getName() + ".competitions");
            createCache(cm, com.betpreview.betmanage.domain.Country.class.getName() + ".teams");
            createCache(cm, com.betpreview.betmanage.domain.Competition.class.getName() + ".matchPreviews");
            createCache(cm, com.betpreview.betmanage.domain.MatchPreview.class.getName() + ".parts");
            createCache(cm, com.betpreview.betmanage.domain.Parts.class.getName());
            createCache(cm, com.betpreview.betmanage.domain.TeamSocial.class.getName());
            createCache(cm, com.betpreview.betmanage.domain.TeamSocial.class.getName() + ".socialMediaMatches");
            createCache(cm, com.betpreview.betmanage.domain.Team.class.getName() + ".competitions");
            createCache(cm, com.betpreview.betmanage.domain.Country.class.getName() + ".matchPreviews");
            createCache(cm, com.betpreview.betmanage.domain.TeamSocial.class.getName() + ".socialMedias");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
