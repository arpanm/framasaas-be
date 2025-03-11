import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './service-order-franchise-assignment.reducer';

export const ServiceOrderFranchiseAssignmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serviceOrderFranchiseAssignmentEntity = useAppSelector(state => state.serviceOrderFranchiseAssignment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviceOrderFranchiseAssignmentDetailsHeading">
          <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.detail.title">ServiceOrderFranchiseAssignment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.id}</dd>
          <dt>
            <span id="serviceOrderAssignmentStatus">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.serviceOrderAssignmentStatus">
                Service Order Assignment Status
              </Translate>
            </span>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.serviceOrderAssignmentStatus}</dd>
          <dt>
            <span id="nps">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.nps">Nps</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.nps}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="assignedTime">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.assignedTime">Assigned Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFranchiseAssignmentEntity.assignedTime ? (
              <TextFormat value={serviceOrderFranchiseAssignmentEntity.assignedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="movedBackTime">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.movedBackTime">Moved Back Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFranchiseAssignmentEntity.movedBackTime ? (
              <TextFormat value={serviceOrderFranchiseAssignmentEntity.movedBackTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="visitTime">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.visitTime">Visit Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFranchiseAssignmentEntity.visitTime ? (
              <TextFormat value={serviceOrderFranchiseAssignmentEntity.visitTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="spareOrderTime">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.spareOrderTime">Spare Order Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFranchiseAssignmentEntity.spareOrderTime ? (
              <TextFormat value={serviceOrderFranchiseAssignmentEntity.spareOrderTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="spareDeliveryTime">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.spareDeliveryTime">Spare Delivery Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFranchiseAssignmentEntity.spareDeliveryTime ? (
              <TextFormat value={serviceOrderFranchiseAssignmentEntity.spareDeliveryTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="completedTime">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.completedTime">Completed Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFranchiseAssignmentEntity.completedTime ? (
              <TextFormat value={serviceOrderFranchiseAssignmentEntity.completedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="completionOTP">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.completionOTP">Completion OTP</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.completionOTP}</dd>
          <dt>
            <span id="cancellationOTP">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.cancellationOTP">Cancellation OTP</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.cancellationOTP}</dd>
          <dt>
            <span id="franchiseCommision">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.franchiseCommision">Franchise Commision</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.franchiseCommision}</dd>
          <dt>
            <span id="franchiseCommisionTax">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.franchiseCommisionTax">Franchise Commision Tax</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.franchiseCommisionTax}</dd>
          <dt>
            <span id="franchiseInvoicePath">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.franchiseInvoicePath">Franchise Invoice Path</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.franchiseInvoicePath}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFranchiseAssignmentEntity.createdTime ? (
              <TextFormat value={serviceOrderFranchiseAssignmentEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderFranchiseAssignmentEntity.updatedTime ? (
              <TextFormat value={serviceOrderFranchiseAssignmentEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.serviceOrder">Service Order</Translate>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.serviceOrder ? serviceOrderFranchiseAssignmentEntity.serviceOrder.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.franchise">Franchise</Translate>
          </dt>
          <dd>{serviceOrderFranchiseAssignmentEntity.franchise ? serviceOrderFranchiseAssignmentEntity.franchise.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-order-franchise-assignment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/service-order-franchise-assignment/${serviceOrderFranchiseAssignmentEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServiceOrderFranchiseAssignmentDetail;
