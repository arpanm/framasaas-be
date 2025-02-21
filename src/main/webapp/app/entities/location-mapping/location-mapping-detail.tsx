import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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
