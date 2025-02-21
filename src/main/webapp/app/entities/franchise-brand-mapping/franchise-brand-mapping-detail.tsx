import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
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
            <span id="createddBy">
              <Translate contentKey="framasaasApp.franchiseBrandMapping.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{franchiseBrandMappingEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.franchiseBrandMapping.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseBrandMappingEntity.createdTime ? (
              <TextFormat value={franchiseBrandMappingEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.franchiseBrandMapping.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{franchiseBrandMappingEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.franchiseBrandMapping.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseBrandMappingEntity.updatedTime ? (
              <TextFormat value={franchiseBrandMappingEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
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
