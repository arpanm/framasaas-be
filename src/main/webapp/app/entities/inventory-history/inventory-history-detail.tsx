import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inventory-history.reducer';

export const InventoryHistoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inventoryHistoryEntity = useAppSelector(state => state.inventoryHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inventoryHistoryDetailsHeading">
          <Translate contentKey="framasaasApp.inventoryHistory.detail.title">InventoryHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{inventoryHistoryEntity.id}</dd>
          <dt>
            <span id="inventoryValue">
              <Translate contentKey="framasaasApp.inventoryHistory.inventoryValue">Inventory Value</Translate>
            </span>
          </dt>
          <dd>{inventoryHistoryEntity.inventoryValue}</dd>
          <dt>
            <span id="reason">
              <Translate contentKey="framasaasApp.inventoryHistory.reason">Reason</Translate>
            </span>
          </dt>
          <dd>{inventoryHistoryEntity.reason}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.inventoryHistory.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{inventoryHistoryEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.inventoryHistory.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {inventoryHistoryEntity.updatedTime ? (
              <TextFormat value={inventoryHistoryEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/inventory-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inventory-history/${inventoryHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InventoryHistoryDetail;
