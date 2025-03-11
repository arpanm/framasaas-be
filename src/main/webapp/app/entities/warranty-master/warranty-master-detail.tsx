import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './warranty-master.reducer';

export const WarrantyMasterDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const warrantyMasterEntity = useAppSelector(state => state.warrantyMaster.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="warrantyMasterDetailsHeading">
          <Translate contentKey="framasaasApp.warrantyMaster.detail.title">WarrantyMaster</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="framasaasApp.warrantyMaster.name">Name</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterEntity.name}</dd>
          <dt>
            <span id="vendorWarrantyMasterId">
              <Translate contentKey="framasaasApp.warrantyMaster.vendorWarrantyMasterId">Vendor Warranty Master Id</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterEntity.vendorWarrantyMasterId}</dd>
          <dt>
            <span id="warrantyType">
              <Translate contentKey="framasaasApp.warrantyMaster.warrantyType">Warranty Type</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterEntity.warrantyType}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="framasaasApp.warrantyMaster.description">Description</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterEntity.description}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="framasaasApp.warrantyMaster.price">Price</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterEntity.price}</dd>
          <dt>
            <span id="periodInMonths">
              <Translate contentKey="framasaasApp.warrantyMaster.periodInMonths">Period In Months</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterEntity.periodInMonths}</dd>
          <dt>
            <span id="taxRate">
              <Translate contentKey="framasaasApp.warrantyMaster.taxRate">Tax Rate</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterEntity.taxRate}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="framasaasApp.warrantyMaster.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.warrantyMaster.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.warrantyMaster.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {warrantyMasterEntity.createdTime ? (
              <TextFormat value={warrantyMasterEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.warrantyMaster.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.warrantyMaster.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {warrantyMasterEntity.updatedTime ? (
              <TextFormat value={warrantyMasterEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.warrantyMaster.product">Product</Translate>
          </dt>
          <dd>{warrantyMasterEntity.product ? warrantyMasterEntity.product.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/warranty-master" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/warranty-master/${warrantyMasterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WarrantyMasterDetail;
