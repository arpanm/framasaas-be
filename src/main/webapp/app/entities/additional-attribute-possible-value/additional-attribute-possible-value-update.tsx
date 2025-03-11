import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getAdditionalAttributes } from 'app/entities/additional-attribute/additional-attribute.reducer';
import { createEntity, getEntity, updateEntity } from './additional-attribute-possible-value.reducer';

export const AdditionalAttributePossibleValueUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const additionalAttributes = useAppSelector(state => state.additionalAttribute.entities);
  const additionalAttributePossibleValueEntity = useAppSelector(state => state.additionalAttributePossibleValue.entity);
  const loading = useAppSelector(state => state.additionalAttributePossibleValue.loading);
  const updating = useAppSelector(state => state.additionalAttributePossibleValue.updating);
  const updateSuccess = useAppSelector(state => state.additionalAttributePossibleValue.updateSuccess);

  const handleClose = () => {
    navigate('/additional-attribute-possible-value');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAdditionalAttributes({}));
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
      ...additionalAttributePossibleValueEntity,
      ...values,
      attribute: additionalAttributes.find(it => it.id.toString() === values.attribute?.toString()),
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
          ...additionalAttributePossibleValueEntity,
          createdTime: convertDateTimeFromServer(additionalAttributePossibleValueEntity.createdTime),
          updatedTime: convertDateTimeFromServer(additionalAttributePossibleValueEntity.updatedTime),
          attribute: additionalAttributePossibleValueEntity?.attribute?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="framasaasApp.additionalAttributePossibleValue.home.createOrEditLabel"
            data-cy="AdditionalAttributePossibleValueCreateUpdateHeading"
          >
            <Translate contentKey="framasaasApp.additionalAttributePossibleValue.home.createOrEditLabel">
              Create or edit a AdditionalAttributePossibleValue
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
                  id="additional-attribute-possible-value-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.additionalAttributePossibleValue.possibleValue')}
                id="additional-attribute-possible-value-possibleValue"
                name="possibleValue"
                data-cy="possibleValue"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.additionalAttributePossibleValue.createddBy')}
                id="additional-attribute-possible-value-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.additionalAttributePossibleValue.createdTime')}
                id="additional-attribute-possible-value-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.additionalAttributePossibleValue.updatedBy')}
                id="additional-attribute-possible-value-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.additionalAttributePossibleValue.updatedTime')}
                id="additional-attribute-possible-value-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="additional-attribute-possible-value-attribute"
                name="attribute"
                data-cy="attribute"
                label={translate('framasaasApp.additionalAttributePossibleValue.attribute')}
                type="select"
              >
                <option value="" key="0" />
                {additionalAttributes
                  ? additionalAttributes.map(otherEntity => (
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
                to="/additional-attribute-possible-value"
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

export default AdditionalAttributePossibleValueUpdate;
