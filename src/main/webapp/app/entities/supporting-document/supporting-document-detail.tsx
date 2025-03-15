import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './supporting-document.reducer';

export const SupportingDocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const supportingDocumentEntity = useAppSelector(state => state.supportingDocument.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="supportingDocumentDetailsHeading">
          <Translate contentKey="framasaasApp.supportingDocument.detail.title">SupportingDocument</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{supportingDocumentEntity.id}</dd>
          <dt>
            <span id="documentName">
              <Translate contentKey="framasaasApp.supportingDocument.documentName">Document Name</Translate>
            </span>
          </dt>
          <dd>{supportingDocumentEntity.documentName}</dd>
          <dt>
            <span id="documentType">
              <Translate contentKey="framasaasApp.supportingDocument.documentType">Document Type</Translate>
            </span>
          </dt>
          <dd>{supportingDocumentEntity.documentType}</dd>
          <dt>
            <span id="documentFormat">
              <Translate contentKey="framasaasApp.supportingDocument.documentFormat">Document Format</Translate>
            </span>
          </dt>
          <dd>{supportingDocumentEntity.documentFormat}</dd>
          <dt>
            <span id="documentSize">
              <Translate contentKey="framasaasApp.supportingDocument.documentSize">Document Size</Translate>
            </span>
          </dt>
          <dd>{supportingDocumentEntity.documentSize}</dd>
          <dt>
            <span id="documentPath">
              <Translate contentKey="framasaasApp.supportingDocument.documentPath">Document Path</Translate>
            </span>
          </dt>
          <dd>{supportingDocumentEntity.documentPath}</dd>
          <dt>
            <span id="isValidated">
              <Translate contentKey="framasaasApp.supportingDocument.isValidated">Is Validated</Translate>
            </span>
          </dt>
          <dd>{supportingDocumentEntity.isValidated ? 'true' : 'false'}</dd>
          <dt>
            <span id="validatedBy">
              <Translate contentKey="framasaasApp.supportingDocument.validatedBy">Validated By</Translate>
            </span>
          </dt>
          <dd>{supportingDocumentEntity.validatedBy}</dd>
          <dt>
            <span id="validatedTime">
              <Translate contentKey="framasaasApp.supportingDocument.validatedTime">Validated Time</Translate>
            </span>
          </dt>
          <dd>
            {supportingDocumentEntity.validatedTime ? (
              <TextFormat value={supportingDocumentEntity.validatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.supportingDocument.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{supportingDocumentEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.supportingDocument.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {supportingDocumentEntity.createdTime ? (
              <TextFormat value={supportingDocumentEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.supportingDocument.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{supportingDocumentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.supportingDocument.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {supportingDocumentEntity.updatedTime ? (
              <TextFormat value={supportingDocumentEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.supportingDocument.franchise">Franchise</Translate>
          </dt>
          <dd>{supportingDocumentEntity.franchise ? supportingDocumentEntity.franchise.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.supportingDocument.articleWarranty">Article Warranty</Translate>
          </dt>
          <dd>{supportingDocumentEntity.articleWarranty ? supportingDocumentEntity.articleWarranty.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/supporting-document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/supporting-document/${supportingDocumentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SupportingDocumentDetail;
