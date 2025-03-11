import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './location-mapping.reducer';

export const LocationMappingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locationMappingEntity = useAppSelector(state => state.locationMapping.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locationMappingDetailsHeading">
          <Translate contentKey="framasaasApp.locationMapping.detail.title">LocationMapping</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{locationMappingEntity.id}</dd>
          <dt>
            <span id="locationName">
              <Translate contentKey="framasaasApp.locationMapping.locationName">Location Name</Translate>
            </span>
          </dt>
          <dd>{locationMappingEntity.locationName}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.locationMapping.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{locationMappingEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.locationMapping.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {locationMappingEntity.createdTime ? (
              <TextFormat value={locationMappingEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.locationMapping.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{locationMappingEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.locationMapping.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {locationMappingEntity.updatedTime ? (
              <TextFormat value={locationMappingEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.locationMapping.franchiseRule">Franchise Rule</Translate>
          </dt>
          <dd>{locationMappingEntity.franchiseRule ? locationMappingEntity.franchiseRule.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.locationMapping.fieldAgentSkillRule">Field Agent Skill Rule</Translate>
          </dt>
          <dd>{locationMappingEntity.fieldAgentSkillRule ? locationMappingEntity.fieldAgentSkillRule.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/location-mapping" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/location-mapping/${locationMappingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocationMappingDetail;
