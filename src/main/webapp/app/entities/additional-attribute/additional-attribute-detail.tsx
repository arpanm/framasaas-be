import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './additional-attribute.reducer';

export const AdditionalAttributeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const additionalAttributeEntity = useAppSelector(state => state.additionalAttribute.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="additionalAttributeDetailsHeading">
          <Translate contentKey="framasaasApp.additionalAttribute.detail.title">AdditionalAttribute</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{additionalAttributeEntity.id}</dd>
          <dt>
            <span id="attributeName">
              <Translate contentKey="framasaasApp.additionalAttribute.attributeName">Attribute Name</Translate>
            </span>
          </dt>
          <dd>{additionalAttributeEntity.attributeName}</dd>
          <dt>
            <span id="attributeValue">
              <Translate contentKey="framasaasApp.additionalAttribute.attributeValue">Attribute Value</Translate>
            </span>
          </dt>
          <dd>{additionalAttributeEntity.attributeValue}</dd>
          <dt>
            <span id="attributeType">
              <Translate contentKey="framasaasApp.additionalAttribute.attributeType">Attribute Type</Translate>
            </span>
          </dt>
          <dd>{additionalAttributeEntity.attributeType}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.additionalAttribute.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{additionalAttributeEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.additionalAttribute.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {additionalAttributeEntity.createdTime ? (
              <TextFormat value={additionalAttributeEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.additionalAttribute.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{additionalAttributeEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.additionalAttribute.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {additionalAttributeEntity.updatedTime ? (
              <TextFormat value={additionalAttributeEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/additional-attribute" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/additional-attribute/${additionalAttributeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdditionalAttributeDetail;
