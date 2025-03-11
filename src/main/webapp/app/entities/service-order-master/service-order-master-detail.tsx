import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './service-order-master.reducer';

export const ServiceOrderMasterDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serviceOrderMasterEntity = useAppSelector(state => state.serviceOrderMaster.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviceOrderMasterDetailsHeading">
          <Translate contentKey="framasaasApp.serviceOrderMaster.detail.title">ServiceOrderMaster</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{serviceOrderMasterEntity.id}</dd>
          <dt>
            <span id="serviceOrderType">
              <Translate contentKey="framasaasApp.serviceOrderMaster.serviceOrderType">Service Order Type</Translate>
            </span>
          </dt>
          <dd>{serviceOrderMasterEntity.serviceOrderType}</dd>
          <dt>
            <span id="slaInHours">
              <Translate contentKey="framasaasApp.serviceOrderMaster.slaInHours">Sla In Hours</Translate>
            </span>
          </dt>
          <dd>{serviceOrderMasterEntity.slaInHours}</dd>
          <dt>
            <span id="charge">
              <Translate contentKey="framasaasApp.serviceOrderMaster.charge">Charge</Translate>
            </span>
          </dt>
          <dd>{serviceOrderMasterEntity.charge}</dd>
          <dt>
            <span id="tax">
              <Translate contentKey="framasaasApp.serviceOrderMaster.tax">Tax</Translate>
            </span>
          </dt>
          <dd>{serviceOrderMasterEntity.tax}</dd>
          <dt>
            <span id="franchiseCommissionWithinSla">
              <Translate contentKey="framasaasApp.serviceOrderMaster.franchiseCommissionWithinSla">
                Franchise Commission Within Sla
              </Translate>
            </span>
          </dt>
          <dd>{serviceOrderMasterEntity.franchiseCommissionWithinSla}</dd>
          <dt>
            <span id="franchiseChargeWithinSlaTax">
              <Translate contentKey="framasaasApp.serviceOrderMaster.franchiseChargeWithinSlaTax">
                Franchise Charge Within Sla Tax
              </Translate>
            </span>
          </dt>
          <dd>{serviceOrderMasterEntity.franchiseChargeWithinSlaTax}</dd>
          <dt>
            <span id="franchiseCommissionOutsideSla">
              <Translate contentKey="framasaasApp.serviceOrderMaster.franchiseCommissionOutsideSla">
                Franchise Commission Outside Sla
              </Translate>
            </span>
          </dt>
          <dd>{serviceOrderMasterEntity.franchiseCommissionOutsideSla}</dd>
          <dt>
            <span id="franchiseChargeOutsideSlaTax">
              <Translate contentKey="framasaasApp.serviceOrderMaster.franchiseChargeOutsideSlaTax">
                Franchise Charge Outside Sla Tax
              </Translate>
            </span>
          </dt>
          <dd>{serviceOrderMasterEntity.franchiseChargeOutsideSlaTax}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="framasaasApp.serviceOrderMaster.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{serviceOrderMasterEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.serviceOrderMaster.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderMasterEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.serviceOrderMaster.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderMasterEntity.createdTime ? (
              <TextFormat value={serviceOrderMasterEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.serviceOrderMaster.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderMasterEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.serviceOrderMaster.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderMasterEntity.updatedTime ? (
              <TextFormat value={serviceOrderMasterEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.serviceOrderMaster.product">Product</Translate>
          </dt>
          <dd>{serviceOrderMasterEntity.product ? serviceOrderMasterEntity.product.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-order-master" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-order-master/${serviceOrderMasterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServiceOrderMasterDetail;
