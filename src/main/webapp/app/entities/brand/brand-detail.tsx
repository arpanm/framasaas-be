import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './brand.reducer';

export const BrandDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const brandEntity = useAppSelector(state => state.brand.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="brandDetailsHeading">
          <Translate contentKey="framasaasApp.brand.detail.title">Brand</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{brandEntity.id}</dd>
          <dt>
            <span id="brandName">
              <Translate contentKey="framasaasApp.brand.brandName">Brand Name</Translate>
            </span>
          </dt>
          <dd>{brandEntity.brandName}</dd>
          <dt>
            <span id="logoPath">
              <Translate contentKey="framasaasApp.brand.logoPath">Logo Path</Translate>
            </span>
          </dt>
          <dd>{brandEntity.logoPath}</dd>
          <dt>
            <span id="vendorBrandId">
              <Translate contentKey="framasaasApp.brand.vendorBrandId">Vendor Brand Id</Translate>
            </span>
          </dt>
          <dd>{brandEntity.vendorBrandId}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="framasaasApp.brand.description">Description</Translate>
            </span>
          </dt>
          <dd>{brandEntity.description}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.brand.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{brandEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.brand.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>{brandEntity.createdTime ? <TextFormat value={brandEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.brand.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{brandEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.brand.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>{brandEntity.updatedTime ? <TextFormat value={brandEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="framasaasApp.brand.franchiseRule">Franchise Rule</Translate>
          </dt>
          <dd>{brandEntity.franchiseRule ? brandEntity.franchiseRule.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/brand" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/brand/${brandEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BrandDetail;
