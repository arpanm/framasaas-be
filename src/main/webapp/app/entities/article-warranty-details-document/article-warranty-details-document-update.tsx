import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getArticleWarrantyDetails } from 'app/entities/article-warranty-details/article-warranty-details.reducer';
import { createEntity, getEntity, updateEntity } from './article-warranty-details-document.reducer';

export const ArticleWarrantyDetailsDocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const articleWarrantyDetails = useAppSelector(state => state.articleWarrantyDetails.entities);
  const articleWarrantyDetailsDocumentEntity = useAppSelector(state => state.articleWarrantyDetailsDocument.entity);
  const loading = useAppSelector(state => state.articleWarrantyDetailsDocument.loading);
  const updating = useAppSelector(state => state.articleWarrantyDetailsDocument.updating);
  const updateSuccess = useAppSelector(state => state.articleWarrantyDetailsDocument.updateSuccess);

  const handleClose = () => {
    navigate('/article-warranty-details-document');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getArticleWarrantyDetails({}));
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
    values.validatedTime = convertDateTimeToServer(values.validatedTime);
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...articleWarrantyDetailsDocumentEntity,
      ...values,
      articleWarranty: articleWarrantyDetails.find(it => it.id.toString() === values.articleWarranty?.toString()),
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
          validatedTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          ...articleWarrantyDetailsDocumentEntity,
          validatedTime: convertDateTimeFromServer(articleWarrantyDetailsDocumentEntity.validatedTime),
          createdTime: convertDateTimeFromServer(articleWarrantyDetailsDocumentEntity.createdTime),
          updatedTime: convertDateTimeFromServer(articleWarrantyDetailsDocumentEntity.updatedTime),
          articleWarranty: articleWarrantyDetailsDocumentEntity?.articleWarranty?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="framasaasApp.articleWarrantyDetailsDocument.home.createOrEditLabel"
            data-cy="ArticleWarrantyDetailsDocumentCreateUpdateHeading"
          >
            <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.home.createOrEditLabel">
              Create or edit a ArticleWarrantyDetailsDocument
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
                  id="article-warranty-details-document-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetailsDocument.documentPath')}
                id="article-warranty-details-document-documentPath"
                name="documentPath"
                data-cy="documentPath"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetailsDocument.isValidated')}
                id="article-warranty-details-document-isValidated"
                name="isValidated"
                data-cy="isValidated"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetailsDocument.validatedBy')}
                id="article-warranty-details-document-validatedBy"
                name="validatedBy"
                data-cy="validatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetailsDocument.validatedTime')}
                id="article-warranty-details-document-validatedTime"
                name="validatedTime"
                data-cy="validatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetailsDocument.createddBy')}
                id="article-warranty-details-document-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetailsDocument.createdTime')}
                id="article-warranty-details-document-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetailsDocument.updatedBy')}
                id="article-warranty-details-document-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.articleWarrantyDetailsDocument.updatedTime')}
                id="article-warranty-details-document-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="article-warranty-details-document-articleWarranty"
                name="articleWarranty"
                data-cy="articleWarranty"
                label={translate('framasaasApp.articleWarrantyDetailsDocument.articleWarranty')}
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/article-warranty-details-document"
                replace
                color="info"
              >
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

export default ArticleWarrantyDetailsDocumentUpdate;
