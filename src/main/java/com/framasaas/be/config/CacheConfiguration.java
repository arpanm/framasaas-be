package com.framasaas.be.config;

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
            createCache(cm, com.framasaas.be.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.framasaas.be.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.framasaas.be.domain.User.class.getName());
            createCache(cm, com.framasaas.be.domain.Authority.class.getName());
            createCache(cm, com.framasaas.be.domain.User.class.getName() + ".authorities");
            createCache(cm, com.framasaas.be.domain.Franchise.class.getName());
            createCache(cm, com.framasaas.be.domain.Franchise.class.getName() + ".franchiseStatusHistories");
            createCache(cm, com.framasaas.be.domain.Franchise.class.getName() + ".franchisePerformanceHistories");
            createCache(cm, com.framasaas.be.domain.Franchise.class.getName() + ".franchiseDocuments");
            createCache(cm, com.framasaas.be.domain.Franchise.class.getName() + ".franchiseUsers");
            createCache(cm, com.framasaas.be.domain.Franchise.class.getName() + ".serviceOrderFranchiseAssignments");
            createCache(cm, com.framasaas.be.domain.Franchise.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.Franchise.class.getName() + ".brands");
            createCache(cm, com.framasaas.be.domain.Franchise.class.getName() + ".categories");
            createCache(cm, com.framasaas.be.domain.FranchiseStatusHistory.class.getName());
            createCache(cm, com.framasaas.be.domain.FranchiseStatusHistory.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.FranchiseBrandMapping.class.getName());
            createCache(cm, com.framasaas.be.domain.FranchiseCategoryMapping.class.getName());
            createCache(cm, com.framasaas.be.domain.Address.class.getName());
            createCache(cm, com.framasaas.be.domain.Address.class.getName() + ".serviceOrders");
            createCache(cm, com.framasaas.be.domain.Address.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.LocationMapping.class.getName());
            createCache(cm, com.framasaas.be.domain.LocationMapping.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.FranchiseDocument.class.getName());
            createCache(cm, com.framasaas.be.domain.FranchiseDocument.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.FranchisePerformanceHistory.class.getName());
            createCache(cm, com.framasaas.be.domain.FranchisePerformanceHistory.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.FranchiseUser.class.getName());
            createCache(cm, com.framasaas.be.domain.FranchiseUser.class.getName() + ".franchiseUserStatusHistories");
            createCache(cm, com.framasaas.be.domain.FranchiseUser.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.FranchiseUserStatusHistory.class.getName());
            createCache(cm, com.framasaas.be.domain.Customer.class.getName());
            createCache(cm, com.framasaas.be.domain.Customer.class.getName() + ".addresses");
            createCache(cm, com.framasaas.be.domain.Customer.class.getName() + ".articles");
            createCache(cm, com.framasaas.be.domain.Customer.class.getName() + ".serviceOrders");
            createCache(cm, com.framasaas.be.domain.Customer.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.Product.class.getName());
            createCache(cm, com.framasaas.be.domain.Product.class.getName() + ".productPriceHistories");
            createCache(cm, com.framasaas.be.domain.Product.class.getName() + ".warrantyMasters");
            createCache(cm, com.framasaas.be.domain.Product.class.getName() + ".articles");
            createCache(cm, com.framasaas.be.domain.Product.class.getName() + ".serviceOrderMasters");
            createCache(cm, com.framasaas.be.domain.Product.class.getName() + ".serviceOrderSpares");
            createCache(cm, com.framasaas.be.domain.Product.class.getName() + ".inventories");
            createCache(cm, com.framasaas.be.domain.Product.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.Product.class.getName() + ".coveredUnderWarranties");
            createCache(cm, com.framasaas.be.domain.Hsn.class.getName());
            createCache(cm, com.framasaas.be.domain.Hsn.class.getName() + ".products");
            createCache(cm, com.framasaas.be.domain.Hsn.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.ProductPriceHistory.class.getName());
            createCache(cm, com.framasaas.be.domain.ProductPriceHistory.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.AdditionalAttribute.class.getName());
            createCache(cm, com.framasaas.be.domain.AdditionalAttribute.class.getName() + ".additionalAttributePossibleValues");
            createCache(cm, com.framasaas.be.domain.AdditionalAttributePossibleValue.class.getName());
            createCache(cm, com.framasaas.be.domain.Brand.class.getName());
            createCache(cm, com.framasaas.be.domain.Brand.class.getName() + ".products");
            createCache(cm, com.framasaas.be.domain.Brand.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.Category.class.getName());
            createCache(cm, com.framasaas.be.domain.Category.class.getName() + ".products");
            createCache(cm, com.framasaas.be.domain.Category.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.Pincode.class.getName());
            createCache(cm, com.framasaas.be.domain.FranchiseAllocationRuleSet.class.getName());
            createCache(cm, com.framasaas.be.domain.FranchiseAllocationRuleSet.class.getName() + ".franchises");
            createCache(cm, com.framasaas.be.domain.FranchiseAllocationRuleSet.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.FranchiseAllocationRule.class.getName());
            createCache(cm, com.framasaas.be.domain.FranchiseAllocationRule.class.getName() + ".brands");
            createCache(cm, com.framasaas.be.domain.FranchiseAllocationRule.class.getName() + ".categories");
            createCache(cm, com.framasaas.be.domain.FranchiseAllocationRule.class.getName() + ".pincodes");
            createCache(cm, com.framasaas.be.domain.FranchiseAllocationRule.class.getName() + ".locationMappings");
            createCache(cm, com.framasaas.be.domain.FranchiseAllocationRule.class.getName() + ".languageMappings");
            createCache(cm, com.framasaas.be.domain.FranchiseAllocationRule.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.Article.class.getName());
            createCache(cm, com.framasaas.be.domain.Article.class.getName() + ".articleWarrantyDetails");
            createCache(cm, com.framasaas.be.domain.Article.class.getName() + ".serviceOrders");
            createCache(cm, com.framasaas.be.domain.Article.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.ArticleWarrantyDetails.class.getName());
            createCache(cm, com.framasaas.be.domain.ArticleWarrantyDetails.class.getName() + ".articleWarrantyDetailsDocuments");
            createCache(cm, com.framasaas.be.domain.ArticleWarrantyDetails.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.WarrantyMaster.class.getName());
            createCache(cm, com.framasaas.be.domain.WarrantyMaster.class.getName() + ".warrantyMasterPriceHistories");
            createCache(cm, com.framasaas.be.domain.WarrantyMaster.class.getName() + ".articleWarrantyDetails");
            createCache(cm, com.framasaas.be.domain.WarrantyMaster.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.WarrantyMaster.class.getName() + ".coveredSpares");
            createCache(cm, com.framasaas.be.domain.WarrantyMasterPriceHistory.class.getName());
            createCache(cm, com.framasaas.be.domain.WarrantyMasterPriceHistory.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.ArticleWarrantyDetailsDocument.class.getName());
            createCache(cm, com.framasaas.be.domain.ArticleWarrantyDetailsDocument.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.ServiceOrderMaster.class.getName());
            createCache(cm, com.framasaas.be.domain.ServiceOrder.class.getName());
            createCache(cm, com.framasaas.be.domain.ServiceOrder.class.getName() + ".serviceOrderFranchiseAssignments");
            createCache(cm, com.framasaas.be.domain.ServiceOrder.class.getName() + ".serviceOrderSpares");
            createCache(cm, com.framasaas.be.domain.ServiceOrder.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.ServiceOrderPayment.class.getName());
            createCache(cm, com.framasaas.be.domain.ServiceOrderPayment.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.ServiceOrderAssignment.class.getName());
            createCache(cm, com.framasaas.be.domain.ServiceOrderAssignment.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.ServiceOrderFranchiseAssignment.class.getName());
            createCache(cm, com.framasaas.be.domain.ServiceOrderFranchiseAssignment.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.ServiceOrderSpare.class.getName());
            createCache(cm, com.framasaas.be.domain.LanguageMapping.class.getName());
            createCache(cm, com.framasaas.be.domain.FieldAgentSkillRuleSet.class.getName());
            createCache(cm, com.framasaas.be.domain.FieldAgentSkillRuleSet.class.getName() + ".fieldAgentSkillRules");
            createCache(cm, com.framasaas.be.domain.FieldAgentSkillRuleSet.class.getName() + ".franchiseUsers");
            createCache(cm, com.framasaas.be.domain.FieldAgentSkillRuleSet.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.FieldAgentSkillRule.class.getName());
            createCache(cm, com.framasaas.be.domain.FieldAgentSkillRule.class.getName() + ".brands");
            createCache(cm, com.framasaas.be.domain.FieldAgentSkillRule.class.getName() + ".categories");
            createCache(cm, com.framasaas.be.domain.FieldAgentSkillRule.class.getName() + ".pincodes");
            createCache(cm, com.framasaas.be.domain.FieldAgentSkillRule.class.getName() + ".locationMappings");
            createCache(cm, com.framasaas.be.domain.FieldAgentSkillRule.class.getName() + ".languageMappings");
            createCache(cm, com.framasaas.be.domain.FieldAgentSkillRule.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.ServiceOrderFieldAgentAssignment.class.getName());
            createCache(cm, com.framasaas.be.domain.ServiceOrderFieldAgentAssignment.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.InventoryLocation.class.getName());
            createCache(cm, com.framasaas.be.domain.InventoryLocation.class.getName() + ".inventories");
            createCache(cm, com.framasaas.be.domain.InventoryLocation.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.Inventory.class.getName());
            createCache(cm, com.framasaas.be.domain.Inventory.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.InventoryHistory.class.getName());
            createCache(cm, com.framasaas.be.domain.Franchise.class.getName() + ".supportingDocuments");
            createCache(cm, com.framasaas.be.domain.ArticleWarrantyDetails.class.getName() + ".supportingDocuments");
            createCache(cm, com.framasaas.be.domain.SupportingDocument.class.getName());
            createCache(cm, com.framasaas.be.domain.SupportingDocument.class.getName() + ".additionalAttributes");
            createCache(cm, com.framasaas.be.domain.Article.class.getName() + ".supportingDocuments");
            createCache(cm, com.framasaas.be.domain.ServiceOrder.class.getName() + ".supportingDocuments");
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
