import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inventory.reducer';

export const InventoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inventoryEntity = useAppSelector(state => state.inventory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inventoryDetailsHeading">
          <Translate contentKey="framasaasApp.inventory.detail.title">Inventory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{inventoryEntity.id}</dd>
          <dt>
            <span id="inventoryValue">
              <Translate contentKey="framasaasApp.inventory.inventoryValue">Inventory Value</Translate>
            </span>
          </dt>
          <dd>{inventoryEntity.inventoryValue}</dd>
          <dt>
            <span id="isSellable">
              <Translate contentKey="framasaasApp.inventory.isSellable">Is Sellable</Translate>
            </span>
          </dt>
          <dd>{inventoryEntity.isSellable ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.inventory.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{inventoryEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.inventory.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {inventoryEntity.createdTime ? <TextFormat value={inventoryEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.inventory.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{inventoryEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.inventory.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {inventoryEntity.updatedTime ? <TextFormat value={inventoryEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.inventory.product">Product</Translate>
          </dt>
          <dd>{inventoryEntity.product ? inventoryEntity.product.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.inventory.location">Location</Translate>
          </dt>
          <dd>{inventoryEntity.location ? inventoryEntity.location.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inventory" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inventory/${inventoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InventoryDetail;
