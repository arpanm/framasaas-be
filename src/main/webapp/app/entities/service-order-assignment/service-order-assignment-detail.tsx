import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './service-order-assignment.reducer';

export const ServiceOrderAssignmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serviceOrderAssignmentEntity = useAppSelector(state => state.serviceOrderAssignment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviceOrderAssignmentDetailsHeading">
          <Translate contentKey="framasaasApp.serviceOrderAssignment.detail.title">ServiceOrderAssignment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{serviceOrderAssignmentEntity.id}</dd>
          <dt>
            <span id="serviceOrderAssignmentStatus">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.serviceOrderAssignmentStatus">
                Service Order Assignment Status
              </Translate>
            </span>
          </dt>
          <dd>{serviceOrderAssignmentEntity.serviceOrderAssignmentStatus}</dd>
          <dt>
            <span id="nps">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.nps">Nps</Translate>
            </span>
          </dt>
          <dd>{serviceOrderAssignmentEntity.nps}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{serviceOrderAssignmentEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="assignedTime">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.assignedTime">Assigned Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderAssignmentEntity.assignedTime ? (
              <TextFormat value={serviceOrderAssignmentEntity.assignedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="movedBackTime">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.movedBackTime">Moved Back Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderAssignmentEntity.movedBackTime ? (
              <TextFormat value={serviceOrderAssignmentEntity.movedBackTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="visitTime">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.visitTime">Visit Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderAssignmentEntity.visitTime ? (
              <TextFormat value={serviceOrderAssignmentEntity.visitTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="spareOrderTime">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.spareOrderTime">Spare Order Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderAssignmentEntity.spareOrderTime ? (
              <TextFormat value={serviceOrderAssignmentEntity.spareOrderTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="spareDeliveryTime">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.spareDeliveryTime">Spare Delivery Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderAssignmentEntity.spareDeliveryTime ? (
              <TextFormat value={serviceOrderAssignmentEntity.spareDeliveryTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="completedTime">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.completedTime">Completed Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderAssignmentEntity.completedTime ? (
              <TextFormat value={serviceOrderAssignmentEntity.completedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="completionOTP">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.completionOTP">Completion OTP</Translate>
            </span>
          </dt>
          <dd>{serviceOrderAssignmentEntity.completionOTP}</dd>
          <dt>
            <span id="cancellationOTP">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.cancellationOTP">Cancellation OTP</Translate>
            </span>
          </dt>
          <dd>{serviceOrderAssignmentEntity.cancellationOTP}</dd>
          <dt>
            <span id="franchiseCommision">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.franchiseCommision">Franchise Commision</Translate>
            </span>
          </dt>
          <dd>{serviceOrderAssignmentEntity.franchiseCommision}</dd>
          <dt>
            <span id="franchiseCommisionTax">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.franchiseCommisionTax">Franchise Commision Tax</Translate>
            </span>
          </dt>
          <dd>{serviceOrderAssignmentEntity.franchiseCommisionTax}</dd>
          <dt>
            <span id="franchiseInvoicePath">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.franchiseInvoicePath">Franchise Invoice Path</Translate>
            </span>
          </dt>
          <dd>{serviceOrderAssignmentEntity.franchiseInvoicePath}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderAssignmentEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderAssignmentEntity.createdTime ? (
              <TextFormat value={serviceOrderAssignmentEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderAssignmentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.serviceOrderAssignment.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderAssignmentEntity.updatedTime ? (
              <TextFormat value={serviceOrderAssignmentEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.serviceOrderAssignment.serviceOrder">Service Order</Translate>
          </dt>
          <dd>{serviceOrderAssignmentEntity.serviceOrder ? serviceOrderAssignmentEntity.serviceOrder.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.serviceOrderAssignment.franchise">Franchise</Translate>
          </dt>
          <dd>{serviceOrderAssignmentEntity.franchise ? serviceOrderAssignmentEntity.franchise.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-order-assignment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-order-assignment/${serviceOrderAssignmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServiceOrderAssignmentDetail;
