import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
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
            <span id="createddBy">
              <Translate contentKey="framasaasApp.franchiseCategoryMapping.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{franchiseCategoryMappingEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.franchiseCategoryMapping.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseCategoryMappingEntity.createdTime ? (
              <TextFormat value={franchiseCategoryMappingEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.franchiseCategoryMapping.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{franchiseCategoryMappingEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.franchiseCategoryMapping.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseCategoryMappingEntity.updatedTime ? (
              <TextFormat value={franchiseCategoryMappingEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
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
