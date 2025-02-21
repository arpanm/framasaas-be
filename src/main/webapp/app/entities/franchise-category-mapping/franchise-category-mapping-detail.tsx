import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './franchise-category-mapping.reducer';

export const FranchiseCategoryMappingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const franchiseCategoryMappingEntity = useAppSelector(state => state.franchiseCategoryMapping.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="franchiseCategoryMappingDetailsHeading">
          <Translate contentKey="framasaasApp.franchiseCategoryMapping.detail.title">FranchiseCategoryMapping</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{franchiseCategoryMappingEntity.id}</dd>
          <dt>
            <span id="serviceCategory">
              <Translate contentKey="framasaasApp.franchiseCategoryMapping.serviceCategory">Service Category</Translate>
            </span>
          </dt>
          <dd>{franchiseCategoryMappingEntity.serviceCategory}</dd>
          <dt>
            <Translate contentKey="framasaasApp.franchiseCategoryMapping.franchise">Franchise</Translate>
          </dt>
          <dd>{franchiseCategoryMappingEntity.franchise ? franchiseCategoryMappingEntity.franchise.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/franchise-category-mapping" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/franchise-category-mapping/${franchiseCategoryMappingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FranchiseCategoryMappingDetail;
