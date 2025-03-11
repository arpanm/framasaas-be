import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inventory-location.reducer';

export const InventoryLocationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inventoryLocationEntity = useAppSelector(state => state.inventoryLocation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inventoryLocationDetailsHeading">
          <Translate contentKey="framasaasApp.inventoryLocation.detail.title">InventoryLocation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{inventoryLocationEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="framasaasApp.inventoryLocation.name">Name</Translate>
            </span>
          </dt>
          <dd>{inventoryLocationEntity.name}</dd>
          <dt>
            <span id="locationType">
              <Translate contentKey="framasaasApp.inventoryLocation.locationType">Location Type</Translate>
            </span>
          </dt>
          <dd>{inventoryLocationEntity.locationType}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="framasaasApp.inventoryLocation.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{inventoryLocationEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.inventoryLocation.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{inventoryLocationEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.inventoryLocation.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {inventoryLocationEntity.createdTime ? (
              <TextFormat value={inventoryLocationEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.inventoryLocation.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{inventoryLocationEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.inventoryLocation.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {inventoryLocationEntity.updatedTime ? (
              <TextFormat value={inventoryLocationEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/inventory-location" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inventory-location/${inventoryLocationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InventoryLocationDetail;
