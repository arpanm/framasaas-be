import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFranchises } from 'app/entities/franchise/franchise.reducer';
import { getEntities as getFranchiseStatusHistories } from 'app/entities/franchise-status-history/franchise-status-history.reducer';
import { getEntities as getFranchisePerformanceHistories } from 'app/entities/franchise-performance-history/franchise-performance-history.reducer';
import { getEntities as getBrands } from 'app/entities/brand/brand.reducer';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntities as getLocationMappings } from 'app/entities/location-mapping/location-mapping.reducer';
import { getEntities as getFranchiseUsers } from 'app/entities/franchise-user/franchise-user.reducer';
import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { getEntities as getFranchiseDocuments } from 'app/entities/franchise-document/franchise-document.reducer';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { getEntities as getHsns } from 'app/entities/hsn/hsn.reducer';
import { getEntities as getProductPriceHistories } from 'app/entities/product-price-history/product-price-history.reducer';
import { getEntities as getWarrantyMasters } from 'app/entities/warranty-master/warranty-master.reducer';
import { getEntities as getWarrantyMasterPriceHistories } from 'app/entities/warranty-master-price-history/warranty-master-price-history.reducer';
import { getEntities as getArticles } from 'app/entities/article/article.reducer';
import { getEntities as getArticleWarrantyDetails } from 'app/entities/article-warranty-details/article-warranty-details.reducer';
import { getEntities as getArticleWarrantyDetailsDocuments } from 'app/entities/article-warranty-details-document/article-warranty-details-document.reducer';
import { getEntities as getServiceOrders } from 'app/entities/service-order/service-order.reducer';
import { getEntities as getServiceOrderPayments } from 'app/entities/service-order-payment/service-order-payment.reducer';
import { getEntities as getServiceOrderAssignments } from 'app/entities/service-order-assignment/service-order-assignment.reducer';
import { AttributeType } from 'app/shared/model/enumerations/attribute-type.model';
import { createEntity, getEntity, updateEntity } from './additional-attribute.reducer';

export const AdditionalAttributeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const franchises = useAppSelector(state => state.franchise.entities);
  const franchiseStatusHistories = useAppSelector(state => state.franchiseStatusHistory.entities);
  const franchisePerformanceHistories = useAppSelector(state => state.franchisePerformanceHistory.entities);
  const brands = useAppSelector(state => state.brand.entities);
  const categories = useAppSelector(state => state.category.entities);
  const addresses = useAppSelector(state => state.address.entities);
  const locationMappings = useAppSelector(state => state.locationMapping.entities);
  const franchiseUsers = useAppSelector(state => state.franchiseUser.entities);
  const customers = useAppSelector(state => state.customer.entities);
  const franchiseDocuments = useAppSelector(state => state.franchiseDocument.entities);
  const products = useAppSelector(state => state.product.entities);
  const hsns = useAppSelector(state => state.hsn.entities);
  const productPriceHistories = useAppSelector(state => state.productPriceHistory.entities);
  const warrantyMasters = useAppSelector(state => state.warrantyMaster.entities);
  const warrantyMasterPriceHistories = useAppSelector(state => state.warrantyMasterPriceHistory.entities);
  const articles = useAppSelector(state => state.article.entities);
  const articleWarrantyDetails = useAppSelector(state => state.articleWarrantyDetails.entities);
  const articleWarrantyDetailsDocuments = useAppSelector(state => state.articleWarrantyDetailsDocument.entities);
  const serviceOrders = useAppSelector(state => state.serviceOrder.entities);
  const serviceOrderPayments = useAppSelector(state => state.serviceOrderPayment.entities);
  const serviceOrderAssignments = useAppSelector(state => state.serviceOrderAssignment.entities);
  const additionalAttributeEntity = useAppSelector(state => state.additionalAttribute.entity);
  const loading = useAppSelector(state => state.additionalAttribute.loading);
  const updating = useAppSelector(state => state.additionalAttribute.updating);
  const updateSuccess = useAppSelector(state => state.additionalAttribute.updateSuccess);
  const attributeTypeValues = Object.keys(AttributeType);

  const handleClose = () => {
    navigate('/additional-attribute');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFranchises({}));
    dispatch(getFranchiseStatusHistories({}));
    dispatch(getFranchisePerformanceHistories({}));
    dispatch(getBrands({}));
    dispatch(getCategories({}));
    dispatch(getAddresses({}));
    dispatch(getLocationMappings({}));
    dispatch(getFranchiseUsers({}));
    dispatch(getCustomers({}));
    dispatch(getFranchiseDocuments({}));
    dispatch(getProducts({}));
    dispatch(getHsns({}));
    dispatch(getProductPriceHistories({}));
    dispatch(getWarrantyMasters({}));
    dispatch(getWarrantyMasterPriceHistories({}));
    dispatch(getArticles({}));
    dispatch(getArticleWarrantyDetails({}));
    dispatch(getArticleWarrantyDetailsDocuments({}));
    dispatch(getServiceOrders({}));
    dispatch(getServiceOrderPayments({}));
    dispatch(getServiceOrderAssignments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...additionalAttributeEntity,
      ...values,
      franchise: franchises.find(it => it.id.toString() === values.franchise?.toString()),
      franchiseStatus: franchiseStatusHistories.find(it => it.id.toString() === values.franchiseStatus?.toString()),
      franchisePerformance: franchisePerformanceHistories.find(it => it.id.toString() === values.franchisePerformance?.toString()),
      brand: brands.find(it => it.id.toString() === values.brand?.toString()),
      category: categories.find(it => it.id.toString() === values.category?.toString()),
      address: addresses.find(it => it.id.toString() === values.address?.toString()),
      location: locationMappings.find(it => it.id.toString() === values.location?.toString()),
      franchiseUser: franchiseUsers.find(it => it.id.toString() === values.franchiseUser?.toString()),
      customer: customers.find(it => it.id.toString() === values.customer?.toString()),
      document: franchiseDocuments.find(it => it.id.toString() === values.document?.toString()),
      product: products.find(it => it.id.toString() === values.product?.toString()),
      hsn: hsns.find(it => it.id.toString() === values.hsn?.toString()),
      priceHistory: productPriceHistories.find(it => it.id.toString() === values.priceHistory?.toString()),
      warrantyMaster: warrantyMasters.find(it => it.id.toString() === values.warrantyMaster?.toString()),
      warrantyMasterPriceHistory: warrantyMasterPriceHistories.find(
        it => it.id.toString() === values.warrantyMasterPriceHistory?.toString(),
      ),
      article: articles.find(it => it.id.toString() === values.article?.toString()),
      articleWarranty: articleWarrantyDetails.find(it => it.id.toString() === values.articleWarranty?.toString()),
      articleWarrantyDocument: articleWarrantyDetailsDocuments.find(it => it.id.toString() === values.articleWarrantyDocument?.toString()),
      serviceOrder: serviceOrders.find(it => it.id.toString() === values.serviceOrder?.toString()),
      serviceOrderPayment: serviceOrderPayments.find(it => it.id.toString() === values.serviceOrderPayment?.toString()),
      serviceOrderAssignment: serviceOrderAssignments.find(it => it.id.toString() === values.serviceOrderAssignment?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          attributeType: 'ATTRSTRING',
          ...additionalAttributeEntity,
          createdTime: convertDateTimeFromServer(additionalAttributeEntity.createdTime),
          updatedTime: convertDateTimeFromServer(additionalAttributeEntity.updatedTime),
          franchise: additionalAttributeEntity?.franchise?.id,
          franchiseStatus: additionalAttributeEntity?.franchiseStatus?.id,
          franchisePerformance: additionalAttributeEntity?.franchisePerformance?.id,
          brand: additionalAttributeEntity?.brand?.id,
          category: additionalAttributeEntity?.category?.id,
          address: additionalAttributeEntity?.address?.id,
          location: additionalAttributeEntity?.location?.id,
          franchiseUser: additionalAttributeEntity?.franchiseUser?.id,
          customer: additionalAttributeEntity?.customer?.id,
          document: additionalAttributeEntity?.document?.id,
          product: additionalAttributeEntity?.product?.id,
          hsn: additionalAttributeEntity?.hsn?.id,
          priceHistory: additionalAttributeEntity?.priceHistory?.id,
          warrantyMaster: additionalAttributeEntity?.warrantyMaster?.id,
          warrantyMasterPriceHistory: additionalAttributeEntity?.warrantyMasterPriceHistory?.id,
          article: additionalAttributeEntity?.article?.id,
          articleWarranty: additionalAttributeEntity?.articleWarranty?.id,
          articleWarrantyDocument: additionalAttributeEntity?.articleWarrantyDocument?.id,
          serviceOrder: additionalAttributeEntity?.serviceOrder?.id,
          serviceOrderPayment: additionalAttributeEntity?.serviceOrderPayment?.id,
          serviceOrderAssignment: additionalAttributeEntity?.serviceOrderAssignment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.additionalAttribute.home.createOrEditLabel" data-cy="AdditionalAttributeCreateUpdateHeading">
            <Translate contentKey="framasaasApp.additionalAttribute.home.createOrEditLabel">Create or edit a AdditionalAttribute</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="additional-attribute-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.additionalAttribute.attributeName')}
                id="additional-attribute-attributeName"
                name="attributeName"
                data-cy="attributeName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.additionalAttribute.attributeValue')}
                id="additional-attribute-attributeValue"
                name="attributeValue"
                data-cy="attributeValue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.additionalAttribute.attributeType')}
                id="additional-attribute-attributeType"
                name="attributeType"
                data-cy="attributeType"
                type="select"
              >
                {attributeTypeValues.map(attributeType => (
                  <option value={attributeType} key={attributeType}>
                    {translate(`framasaasApp.AttributeType.${attributeType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.additionalAttribute.createddBy')}
                id="additional-attribute-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.additionalAttribute.createdTime')}
                id="additional-attribute-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.additionalAttribute.updatedBy')}
                id="additional-attribute-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.additionalAttribute.updatedTime')}
                id="additional-attribute-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="additional-attribute-franchise"
                name="franchise"
                data-cy="franchise"
                label={translate('framasaasApp.additionalAttribute.franchise')}
                type="select"
              >
                <option value="" key="0" />
                {franchises
                  ? franchises.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-franchiseStatus"
                name="franchiseStatus"
                data-cy="franchiseStatus"
                label={translate('framasaasApp.additionalAttribute.franchiseStatus')}
                type="select"
              >
                <option value="" key="0" />
                {franchiseStatusHistories
                  ? franchiseStatusHistories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-franchisePerformance"
                name="franchisePerformance"
                data-cy="franchisePerformance"
                label={translate('framasaasApp.additionalAttribute.franchisePerformance')}
                type="select"
              >
                <option value="" key="0" />
                {franchisePerformanceHistories
                  ? franchisePerformanceHistories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-brand"
                name="brand"
                data-cy="brand"
                label={translate('framasaasApp.additionalAttribute.brand')}
                type="select"
              >
                <option value="" key="0" />
                {brands
                  ? brands.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-category"
                name="category"
                data-cy="category"
                label={translate('framasaasApp.additionalAttribute.category')}
                type="select"
              >
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-address"
                name="address"
                data-cy="address"
                label={translate('framasaasApp.additionalAttribute.address')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-location"
                name="location"
                data-cy="location"
                label={translate('framasaasApp.additionalAttribute.location')}
                type="select"
              >
                <option value="" key="0" />
                {locationMappings
                  ? locationMappings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-franchiseUser"
                name="franchiseUser"
                data-cy="franchiseUser"
                label={translate('framasaasApp.additionalAttribute.franchiseUser')}
                type="select"
              >
                <option value="" key="0" />
                {franchiseUsers
                  ? franchiseUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-customer"
                name="customer"
                data-cy="customer"
                label={translate('framasaasApp.additionalAttribute.customer')}
                type="select"
              >
                <option value="" key="0" />
                {customers
                  ? customers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-document"
                name="document"
                data-cy="document"
                label={translate('framasaasApp.additionalAttribute.document')}
                type="select"
              >
                <option value="" key="0" />
                {franchiseDocuments
                  ? franchiseDocuments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-product"
                name="product"
                data-cy="product"
                label={translate('framasaasApp.additionalAttribute.product')}
                type="select"
              >
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-hsn"
                name="hsn"
                data-cy="hsn"
                label={translate('framasaasApp.additionalAttribute.hsn')}
                type="select"
              >
                <option value="" key="0" />
                {hsns
                  ? hsns.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-priceHistory"
                name="priceHistory"
                data-cy="priceHistory"
                label={translate('framasaasApp.additionalAttribute.priceHistory')}
                type="select"
              >
                <option value="" key="0" />
                {productPriceHistories
                  ? productPriceHistories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-warrantyMaster"
                name="warrantyMaster"
                data-cy="warrantyMaster"
                label={translate('framasaasApp.additionalAttribute.warrantyMaster')}
                type="select"
              >
                <option value="" key="0" />
                {warrantyMasters
                  ? warrantyMasters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-warrantyMasterPriceHistory"
                name="warrantyMasterPriceHistory"
                data-cy="warrantyMasterPriceHistory"
                label={translate('framasaasApp.additionalAttribute.warrantyMasterPriceHistory')}
                type="select"
              >
                <option value="" key="0" />
                {warrantyMasterPriceHistories
                  ? warrantyMasterPriceHistories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-article"
                name="article"
                data-cy="article"
                label={translate('framasaasApp.additionalAttribute.article')}
                type="select"
              >
                <option value="" key="0" />
                {articles
                  ? articles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-articleWarranty"
                name="articleWarranty"
                data-cy="articleWarranty"
                label={translate('framasaasApp.additionalAttribute.articleWarranty')}
                type="select"
              >
                <option value="" key="0" />
                {articleWarrantyDetails
                  ? articleWarrantyDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-articleWarrantyDocument"
                name="articleWarrantyDocument"
                data-cy="articleWarrantyDocument"
                label={translate('framasaasApp.additionalAttribute.articleWarrantyDocument')}
                type="select"
              >
                <option value="" key="0" />
                {articleWarrantyDetailsDocuments
                  ? articleWarrantyDetailsDocuments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-serviceOrder"
                name="serviceOrder"
                data-cy="serviceOrder"
                label={translate('framasaasApp.additionalAttribute.serviceOrder')}
                type="select"
              >
                <option value="" key="0" />
                {serviceOrders
                  ? serviceOrders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-serviceOrderPayment"
                name="serviceOrderPayment"
                data-cy="serviceOrderPayment"
                label={translate('framasaasApp.additionalAttribute.serviceOrderPayment')}
                type="select"
              >
                <option value="" key="0" />
                {serviceOrderPayments
                  ? serviceOrderPayments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="additional-attribute-serviceOrderAssignment"
                name="serviceOrderAssignment"
                data-cy="serviceOrderAssignment"
                label={translate('framasaasApp.additionalAttribute.serviceOrderAssignment')}
                type="select"
              >
                <option value="" key="0" />
                {serviceOrderAssignments
                  ? serviceOrderAssignments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/additional-attribute" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AdditionalAttributeUpdate;
