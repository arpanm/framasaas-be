import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './additional-attribute.reducer';

export const AdditionalAttributeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const additionalAttributeEntity = useAppSelector(state => state.additionalAttribute.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="additionalAttributeDetailsHeading">
          <Translate contentKey="framasaasApp.additionalAttribute.detail.title">AdditionalAttribute</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{additionalAttributeEntity.id}</dd>
          <dt>
            <span id="attributeName">
              <Translate contentKey="framasaasApp.additionalAttribute.attributeName">Attribute Name</Translate>
            </span>
          </dt>
          <dd>{additionalAttributeEntity.attributeName}</dd>
          <dt>
            <span id="attributeValue">
              <Translate contentKey="framasaasApp.additionalAttribute.attributeValue">Attribute Value</Translate>
            </span>
          </dt>
          <dd>{additionalAttributeEntity.attributeValue}</dd>
          <dt>
            <span id="attributeType">
              <Translate contentKey="framasaasApp.additionalAttribute.attributeType">Attribute Type</Translate>
            </span>
          </dt>
          <dd>{additionalAttributeEntity.attributeType}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.additionalAttribute.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{additionalAttributeEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.additionalAttribute.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {additionalAttributeEntity.createdTime ? (
              <TextFormat value={additionalAttributeEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.additionalAttribute.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{additionalAttributeEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.additionalAttribute.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {additionalAttributeEntity.updatedTime ? (
              <TextFormat value={additionalAttributeEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.franchise">Franchise</Translate>
          </dt>
          <dd>{additionalAttributeEntity.franchise ? additionalAttributeEntity.franchise.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.franchiseStatus">Franchise Status</Translate>
          </dt>
          <dd>{additionalAttributeEntity.franchiseStatus ? additionalAttributeEntity.franchiseStatus.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.franchisePerformance">Franchise Performance</Translate>
          </dt>
          <dd>{additionalAttributeEntity.franchisePerformance ? additionalAttributeEntity.franchisePerformance.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.brand">Brand</Translate>
          </dt>
          <dd>{additionalAttributeEntity.brand ? additionalAttributeEntity.brand.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.category">Category</Translate>
          </dt>
          <dd>{additionalAttributeEntity.category ? additionalAttributeEntity.category.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.address">Address</Translate>
          </dt>
          <dd>{additionalAttributeEntity.address ? additionalAttributeEntity.address.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.location">Location</Translate>
          </dt>
          <dd>{additionalAttributeEntity.location ? additionalAttributeEntity.location.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.franchiseUser">Franchise User</Translate>
          </dt>
          <dd>{additionalAttributeEntity.franchiseUser ? additionalAttributeEntity.franchiseUser.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.customer">Customer</Translate>
          </dt>
          <dd>{additionalAttributeEntity.customer ? additionalAttributeEntity.customer.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.document">Document</Translate>
          </dt>
          <dd>{additionalAttributeEntity.document ? additionalAttributeEntity.document.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.product">Product</Translate>
          </dt>
          <dd>{additionalAttributeEntity.product ? additionalAttributeEntity.product.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.hsn">Hsn</Translate>
          </dt>
          <dd>{additionalAttributeEntity.hsn ? additionalAttributeEntity.hsn.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.priceHistory">Price History</Translate>
          </dt>
          <dd>{additionalAttributeEntity.priceHistory ? additionalAttributeEntity.priceHistory.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.warrantyMaster">Warranty Master</Translate>
          </dt>
          <dd>{additionalAttributeEntity.warrantyMaster ? additionalAttributeEntity.warrantyMaster.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.warrantyMasterPriceHistory">Warranty Master Price History</Translate>
          </dt>
          <dd>{additionalAttributeEntity.warrantyMasterPriceHistory ? additionalAttributeEntity.warrantyMasterPriceHistory.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.article">Article</Translate>
          </dt>
          <dd>{additionalAttributeEntity.article ? additionalAttributeEntity.article.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.articleWarranty">Article Warranty</Translate>
          </dt>
          <dd>{additionalAttributeEntity.articleWarranty ? additionalAttributeEntity.articleWarranty.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.articleWarrantyDocument">Article Warranty Document</Translate>
          </dt>
          <dd>{additionalAttributeEntity.articleWarrantyDocument ? additionalAttributeEntity.articleWarrantyDocument.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.serviceOrder">Service Order</Translate>
          </dt>
          <dd>{additionalAttributeEntity.serviceOrder ? additionalAttributeEntity.serviceOrder.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.serviceOrderPayment">Service Order Payment</Translate>
          </dt>
          <dd>{additionalAttributeEntity.serviceOrderPayment ? additionalAttributeEntity.serviceOrderPayment.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.serviceOrderFranchiseAssignment">
              Service Order Franchise Assignment
            </Translate>
          </dt>
          <dd>
            {additionalAttributeEntity.serviceOrderFranchiseAssignment ? additionalAttributeEntity.serviceOrderFranchiseAssignment.id : ''}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.serviceOrderFieldAgentAssignment">
              Service Order Field Agent Assignment
            </Translate>
          </dt>
          <dd>
            {additionalAttributeEntity.serviceOrderFieldAgentAssignment
              ? additionalAttributeEntity.serviceOrderFieldAgentAssignment.id
              : ''}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.franchiseAllocationRuleSet">Franchise Allocation Rule Set</Translate>
          </dt>
          <dd>{additionalAttributeEntity.franchiseAllocationRuleSet ? additionalAttributeEntity.franchiseAllocationRuleSet.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.franchiseAllocationRule">Franchise Allocation Rule</Translate>
          </dt>
          <dd>{additionalAttributeEntity.franchiseAllocationRule ? additionalAttributeEntity.franchiseAllocationRule.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.fieldAgentSkillRuleSet">Field Agent Skill Rule Set</Translate>
          </dt>
          <dd>{additionalAttributeEntity.fieldAgentSkillRuleSet ? additionalAttributeEntity.fieldAgentSkillRuleSet.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.fieldAgentSkillRule">Field Agent Skill Rule</Translate>
          </dt>
          <dd>{additionalAttributeEntity.fieldAgentSkillRule ? additionalAttributeEntity.fieldAgentSkillRule.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.inventoryLocation">Inventory Location</Translate>
          </dt>
          <dd>{additionalAttributeEntity.inventoryLocation ? additionalAttributeEntity.inventoryLocation.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.inventory">Inventory</Translate>
          </dt>
          <dd>{additionalAttributeEntity.inventory ? additionalAttributeEntity.inventory.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.additionalAttribute.serviceOrderAssignment">Service Order Assignment</Translate>
          </dt>
          <dd>{additionalAttributeEntity.serviceOrderAssignment ? additionalAttributeEntity.serviceOrderAssignment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/additional-attribute" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/additional-attribute/${additionalAttributeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdditionalAttributeDetail;
