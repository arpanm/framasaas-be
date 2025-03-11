import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './warranty-master-price-history.reducer';

export const WarrantyMasterPriceHistoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const warrantyMasterPriceHistoryEntity = useAppSelector(state => state.warrantyMasterPriceHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="warrantyMasterPriceHistoryDetailsHeading">
          <Translate contentKey="framasaasApp.warrantyMasterPriceHistory.detail.title">WarrantyMasterPriceHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterPriceHistoryEntity.id}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="framasaasApp.warrantyMasterPriceHistory.price">Price</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterPriceHistoryEntity.price}</dd>
          <dt>
            <span id="tax">
              <Translate contentKey="framasaasApp.warrantyMasterPriceHistory.tax">Tax</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterPriceHistoryEntity.tax}</dd>
          <dt>
            <span id="franchiseCommission">
              <Translate contentKey="framasaasApp.warrantyMasterPriceHistory.franchiseCommission">Franchise Commission</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterPriceHistoryEntity.franchiseCommission}</dd>
          <dt>
            <span id="franchiseTax">
              <Translate contentKey="framasaasApp.warrantyMasterPriceHistory.franchiseTax">Franchise Tax</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterPriceHistoryEntity.franchiseTax}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.warrantyMasterPriceHistory.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{warrantyMasterPriceHistoryEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.warrantyMasterPriceHistory.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {warrantyMasterPriceHistoryEntity.updatedTime ? (
              <TextFormat value={warrantyMasterPriceHistoryEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.warrantyMasterPriceHistory.warrantyMaster">Warranty Master</Translate>
          </dt>
          <dd>{warrantyMasterPriceHistoryEntity.warrantyMaster ? warrantyMasterPriceHistoryEntity.warrantyMaster.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/warranty-master-price-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/warranty-master-price-history/${warrantyMasterPriceHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WarrantyMasterPriceHistoryDetail;
