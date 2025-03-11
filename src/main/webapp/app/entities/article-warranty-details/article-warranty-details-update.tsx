import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getArticles } from 'app/entities/article/article.reducer';
import { getEntities as getWarrantyMasters } from 'app/entities/warranty-master/warranty-master.reducer';
import { WarrantyType } from 'app/shared/model/enumerations/warranty-type.model';
import { createEntity, getEntity, updateEntity } from './article-warranty-details.reducer';

export const ArticleWarrantyDetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const articles = useAppSelector(state => state.article.entities);
  const warrantyMasters = useAppSelector(state => state.warrantyMaster.entities);
  const articleWarrantyDetailsEntity = useAppSelector(state => state.articleWarrantyDetails.entity);
  const loading = useAppSelector(state => state.articleWarrantyDetails.loading);
  const updating = useAppSelector(state => state.articleWarrantyDetails.updating);
  const updateSuccess = useAppSelector(state => state.articleWarrantyDetails.updateSuccess);
  const warrantyTypeValues = Object.keys(WarrantyType);

  const handleClose = () => {
    navigate('/article-warranty-details');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getArticles({}));
    dispatch(getWarrantyMasters({}));
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
      ...articleWarrantyDetailsEntity,
      ...values,
      article: articles.find(it => it.id.toString() === values.article?.toString()),
      warrantyMaster: warrantyMasters.find(it => it.id.toString() === values.warrantyMaster?.toString()),
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
          warrantyType: 'BRANDFREEWARRANTY',
          ...articleWarrantyDetailsEntity,
          createdTime: convertDateTimeFromServer(articleWarrantyDetailsEntity.createdTime),
          updatedTime: convertDateTimeFromServer(articleWarrantyDetailsEntity.updatedTime),
          article: articleWarrantyDetailsEntity?.article?.id,
          warrantyMaster: articleWarrantyDetailsEntity?.warrantyMaster?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.articleWarrantyDetails.home.createOrEditLabel" data-cy="ArticleWarrantyDetailsCreateUpdateHeading">
            <Translate contentKey="framasaasApp.articleWarrantyDetails.home.createOrEditLabel">
              Create or edit a ArticleWarrantyDetails
            </Translate>
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
                  id="article-warranty-details-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetails.warrantyType')}
                id="article-warranty-details-warrantyType"
                name="warrantyType"
                data-cy="warrantyType"
                type="select"
              >
                {warrantyTypeValues.map(warrantyType => (
                  <option value={warrantyType} key={warrantyType}>
                    {translate(`framasaasApp.WarrantyType.${warrantyType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetails.vendorArticleWarrantyId')}
                id="article-warranty-details-vendorArticleWarrantyId"
                name="vendorArticleWarrantyId"
                data-cy="vendorArticleWarrantyId"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetails.vendorWarrantyMasterId')}
                id="article-warranty-details-vendorWarrantyMasterId"
                name="vendorWarrantyMasterId"
                data-cy="vendorWarrantyMasterId"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetails.startDate')}
                id="article-warranty-details-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetails.endDate')}
                id="article-warranty-details-endDate"
                name="endDate"
                data-cy="endDate"
                type="date"
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetails.isActive')}
                id="article-warranty-details-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetails.createddBy')}
                id="article-warranty-details-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetails.createdTime')}
                id="article-warranty-details-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetails.updatedBy')}
                id="article-warranty-details-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetails.updatedTime')}
                id="article-warranty-details-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="article-warranty-details-article"
                name="article"
                data-cy="article"
                label={translate('framasaasApp.articleWarrantyDetails.article')}
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
                id="article-warranty-details-warrantyMaster"
                name="warrantyMaster"
                data-cy="warrantyMaster"
                label={translate('framasaasApp.articleWarrantyDetails.warrantyMaster')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/article-warranty-details" replace color="info">
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

export default ArticleWarrantyDetailsUpdate;
