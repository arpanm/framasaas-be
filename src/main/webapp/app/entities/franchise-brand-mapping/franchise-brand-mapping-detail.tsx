import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './franchise-brand-mapping.reducer';

export const FranchiseBrandMappingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const franchiseBrandMappingEntity = useAppSelector(state => state.franchiseBrandMapping.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="franchiseBrandMappingDetailsHeading">
          <Translate contentKey="framasaasApp.franchiseBrandMapping.detail.title">FranchiseBrandMapping</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{franchiseBrandMappingEntity.id}</dd>
          <dt>
            <span id="brand">
              <Translate contentKey="framasaasApp.franchiseBrandMapping.brand">Brand</Translate>
            </span>
          </dt>
          <dd>{franchiseBrandMappingEntity.brand}</dd>
          <dt>
            <Translate contentKey="framasaasApp.franchiseBrandMapping.franchise">Franchise</Translate>
          </dt>
          <dd>{franchiseBrandMappingEntity.franchise ? franchiseBrandMappingEntity.franchise.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/franchise-brand-mapping" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/franchise-brand-mapping/${franchiseBrandMappingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FranchiseBrandMappingDetail;
