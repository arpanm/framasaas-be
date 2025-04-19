package com.framasaas.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.framasaas.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.framasaas.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.framasaas.domain.User.class.getName());
            createCache(cm, com.framasaas.domain.Authority.class.getName());
            createCache(cm, com.framasaas.domain.User.class.getName() + ".authorities");
            createCache(cm, com.framasaas.domain.Franchise.class.getName());
            createCache(cm, com.framasaas.domain.Franchise.class.getName() + ".franchiseStatusHistories");
            createCache(cm, com.framasaas.domain.Franchise.class.getName() + ".franchisePerformanceHistories");
            createCache(cm, com.framasaas.domain.Franchise.class.getName() + ".supportingDocuments");
            createCache(cm, com.framasaas.domain.Franchise.class.getName() + ".franchiseUsers");
            createCache(cm, com.framasaas.domain.Franchise.class.getName() + ".serviceOrderFranchiseAssignments");
            createCache(cm, com.framasaas.domain.Franchise.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.Franchise.class.getName() + ".brands");
            createCache(cm, com.framasaas.domain.Franchise.class.getName() + ".categories");
            createCache(cm, com.framasaas.domain.FranchiseStatusHistory.class.getName());
            createCache(cm, com.framasaas.domain.FranchiseStatusHistory.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.FranchiseBrandMapping.class.getName());
            createCache(cm, com.framasaas.domain.FranchiseCategoryMapping.class.getName());
            createCache(cm, com.framasaas.domain.Address.class.getName());
            createCache(cm, com.framasaas.domain.Address.class.getName() + ".serviceOrders");
            createCache(cm, com.framasaas.domain.Address.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.LocationMapping.class.getName());
            createCache(cm, com.framasaas.domain.LocationMapping.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.FranchiseDocument.class.getName());
            createCache(cm, com.framasaas.domain.FranchiseDocument.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.FranchisePerformanceHistory.class.getName());
            createCache(cm, com.framasaas.domain.FranchisePerformanceHistory.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.FranchiseUser.class.getName());
            createCache(cm, com.framasaas.domain.FranchiseUser.class.getName() + ".franchiseUserStatusHistories");
            createCache(cm, com.framasaas.domain.FranchiseUser.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.FranchiseUserStatusHistory.class.getName());
            createCache(cm, com.framasaas.domain.Customer.class.getName());
            createCache(cm, com.framasaas.domain.Customer.class.getName() + ".addresses");
            createCache(cm, com.framasaas.domain.Customer.class.getName() + ".articles");
            createCache(cm, com.framasaas.domain.Customer.class.getName() + ".serviceOrders");
            createCache(cm, com.framasaas.domain.Customer.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.Product.class.getName());
            createCache(cm, com.framasaas.domain.Product.class.getName() + ".productPriceHistories");
            createCache(cm, com.framasaas.domain.Product.class.getName() + ".warrantyMasters");
            createCache(cm, com.framasaas.domain.Product.class.getName() + ".articles");
            createCache(cm, com.framasaas.domain.Product.class.getName() + ".serviceOrderMasters");
            createCache(cm, com.framasaas.domain.Product.class.getName() + ".serviceOrderSpares");
            createCache(cm, com.framasaas.domain.Product.class.getName() + ".inventories");
            createCache(cm, com.framasaas.domain.Product.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.Product.class.getName() + ".coveredUnderWarranties");
            createCache(cm, com.framasaas.domain.Hsn.class.getName());
            createCache(cm, com.framasaas.domain.Hsn.class.getName() + ".products");
            createCache(cm, com.framasaas.domain.Hsn.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.ProductPriceHistory.class.getName());
            createCache(cm, com.framasaas.domain.ProductPriceHistory.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.AdditionalAttribute.class.getName());
            createCache(cm, com.framasaas.domain.AdditionalAttribute.class.getName() + ".additionalAttributePossibleValues");
            createCache(cm, com.framasaas.domain.AdditionalAttributePossibleValue.class.getName());
            createCache(cm, com.framasaas.domain.Brand.class.getName());
            createCache(cm, com.framasaas.domain.Brand.class.getName() + ".products");
            createCache(cm, com.framasaas.domain.Brand.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.Category.class.getName());
            createCache(cm, com.framasaas.domain.Category.class.getName() + ".products");
            createCache(cm, com.framasaas.domain.Category.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.Pincode.class.getName());
            createCache(cm, com.framasaas.domain.FranchiseAllocationRuleSet.class.getName());
            createCache(cm, com.framasaas.domain.FranchiseAllocationRuleSet.class.getName() + ".franchises");
            createCache(cm, com.framasaas.domain.FranchiseAllocationRuleSet.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.FranchiseAllocationRule.class.getName());
            createCache(cm, com.framasaas.domain.FranchiseAllocationRule.class.getName() + ".brands");
            createCache(cm, com.framasaas.domain.FranchiseAllocationRule.class.getName() + ".categories");
            createCache(cm, com.framasaas.domain.FranchiseAllocationRule.class.getName() + ".pincodes");
            createCache(cm, com.framasaas.domain.FranchiseAllocationRule.class.getName() + ".locationMappings");
            createCache(cm, com.framasaas.domain.FranchiseAllocationRule.class.getName() + ".languageMappings");
            createCache(cm, com.framasaas.domain.FranchiseAllocationRule.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.Article.class.getName());
            createCache(cm, com.framasaas.domain.Article.class.getName() + ".supportingDocuments");
            createCache(cm, com.framasaas.domain.Article.class.getName() + ".articleWarrantyDetails");
            createCache(cm, com.framasaas.domain.Article.class.getName() + ".serviceOrders");
            createCache(cm, com.framasaas.domain.Article.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.ArticleWarrantyDetails.class.getName());
            createCache(cm, com.framasaas.domain.ArticleWarrantyDetails.class.getName() + ".supportingDocuments");
            createCache(cm, com.framasaas.domain.ArticleWarrantyDetails.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.WarrantyMaster.class.getName());
            createCache(cm, com.framasaas.domain.WarrantyMaster.class.getName() + ".warrantyMasterPriceHistories");
            createCache(cm, com.framasaas.domain.WarrantyMaster.class.getName() + ".articleWarrantyDetails");
            createCache(cm, com.framasaas.domain.WarrantyMaster.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.WarrantyMaster.class.getName() + ".coveredSpares");
            createCache(cm, com.framasaas.domain.WarrantyMasterPriceHistory.class.getName());
            createCache(cm, com.framasaas.domain.WarrantyMasterPriceHistory.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.ArticleWarrantyDetailsDocument.class.getName());
            createCache(cm, com.framasaas.domain.ArticleWarrantyDetailsDocument.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.ServiceOrderMaster.class.getName());
            createCache(cm, com.framasaas.domain.ServiceOrderMaster.class.getName() + ".serviceOrders");
            createCache(cm, com.framasaas.domain.ServiceOrder.class.getName());
            createCache(cm, com.framasaas.domain.ServiceOrder.class.getName() + ".supportingDocuments");
            createCache(cm, com.framasaas.domain.ServiceOrder.class.getName() + ".serviceOrderFranchiseAssignments");
            createCache(cm, com.framasaas.domain.ServiceOrder.class.getName() + ".serviceOrderSpares");
            createCache(cm, com.framasaas.domain.ServiceOrder.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.ServiceOrderPayment.class.getName());
            createCache(cm, com.framasaas.domain.ServiceOrderPayment.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.ServiceOrderAssignment.class.getName());
            createCache(cm, com.framasaas.domain.ServiceOrderAssignment.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.ServiceOrderFranchiseAssignment.class.getName());
            createCache(cm, com.framasaas.domain.ServiceOrderFranchiseAssignment.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.ServiceOrderSpare.class.getName());
            createCache(cm, com.framasaas.domain.LanguageMapping.class.getName());
            createCache(cm, com.framasaas.domain.FieldAgentSkillRuleSet.class.getName());
            createCache(cm, com.framasaas.domain.FieldAgentSkillRuleSet.class.getName() + ".fieldAgentSkillRules");
            createCache(cm, com.framasaas.domain.FieldAgentSkillRuleSet.class.getName() + ".franchiseUsers");
            createCache(cm, com.framasaas.domain.FieldAgentSkillRuleSet.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.FieldAgentSkillRule.class.getName());
            createCache(cm, com.framasaas.domain.FieldAgentSkillRule.class.getName() + ".brands");
            createCache(cm, com.framasaas.domain.FieldAgentSkillRule.class.getName() + ".categories");
            createCache(cm, com.framasaas.domain.FieldAgentSkillRule.class.getName() + ".pincodes");
            createCache(cm, com.framasaas.domain.FieldAgentSkillRule.class.getName() + ".locationMappings");
            createCache(cm, com.framasaas.domain.FieldAgentSkillRule.class.getName() + ".languageMappings");
            createCache(cm, com.framasaas.domain.FieldAgentSkillRule.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.ServiceOrderFieldAgentAssignment.class.getName());
            createCache(cm, com.framasaas.domain.ServiceOrderFieldAgentAssignment.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.InventoryLocation.class.getName());
            createCache(cm, com.framasaas.domain.InventoryLocation.class.getName() + ".inventories");
            createCache(cm, com.framasaas.domain.InventoryLocation.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.Inventory.class.getName());
            createCache(cm, com.framasaas.domain.Inventory.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.domain.InventoryHistory.class.getName());
            createCache(cm, com.framasaas.domain.SupportingDocument.class.getName());
            createCache(cm, com.framasaas.domain.SupportingDocument.class.getName() + ".additionalAttributes");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
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
