import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './additional-attribute-possible-value.reducer';

export const AdditionalAttributePossibleValueDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const additionalAttributePossibleValueEntity = useAppSelector(state => state.additionalAttributePossibleValue.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="additionalAttributePossibleValueDetailsHeading">
          <Translate contentKey="framasaasApp.additionalAttributePossibleValue.detail.title">AdditionalAttributePossibleValue</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{additionalAttributePossibleValueEntity.id}</dd>
          <dt>
            <span id="possibleValue">
              <Translate contentKey="framasaasApp.additionalAttributePossibleValue.possibleValue">Possible Value</Translate>
            </span>
          </dt>
          <dd>{additionalAttributePossibleValueEntity.possibleValue}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.additionalAttributePossibleValue.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{additionalAttributePossibleValueEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.additionalAttributePossibleValue.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {additionalAttributePossibleValueEntity.createdTime ? (
              <TextFormat value={additionalAttributePossibleValueEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.additionalAttributePossibleValue.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{additionalAttributePossibleValueEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.additionalAttributePossibleValue.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {additionalAttributePossibleValueEntity.updatedTime ? (
              <TextFormat value={additionalAttributePossibleValueEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttributePossibleValue.attribute">Attribute</Translate>
          </dt>
          <dd>{additionalAttributePossibleValueEntity.attribute ? additionalAttributePossibleValueEntity.attribute.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/additional-attribute-possible-value" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/additional-attribute-possible-value/${additionalAttributePossibleValueEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdditionalAttributePossibleValueDetail;
