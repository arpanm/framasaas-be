import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './franchise-document.reducer';

export const FranchiseDocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const franchiseDocumentEntity = useAppSelector(state => state.franchiseDocument.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="franchiseDocumentDetailsHeading">
          <Translate contentKey="framasaasApp.franchiseDocument.detail.title">FranchiseDocument</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{franchiseDocumentEntity.id}</dd>
          <dt>
            <span id="documentName">
              <Translate contentKey="framasaasApp.franchiseDocument.documentName">Document Name</Translate>
            </span>
          </dt>
          <dd>{franchiseDocumentEntity.documentName}</dd>
          <dt>
            <span id="documentType">
              <Translate contentKey="framasaasApp.franchiseDocument.documentType">Document Type</Translate>
            </span>
          </dt>
          <dd>{franchiseDocumentEntity.documentType}</dd>
          <dt>
            <span id="documentFormat">
              <Translate contentKey="framasaasApp.franchiseDocument.documentFormat">Document Format</Translate>
            </span>
          </dt>
          <dd>{franchiseDocumentEntity.documentFormat}</dd>
          <dt>
            <span id="documentSize">
              <Translate contentKey="framasaasApp.franchiseDocument.documentSize">Document Size</Translate>
            </span>
          </dt>
          <dd>{franchiseDocumentEntity.documentSize}</dd>
          <dt>
            <span id="documentPath">
              <Translate contentKey="framasaasApp.franchiseDocument.documentPath">Document Path</Translate>
            </span>
          </dt>
          <dd>{franchiseDocumentEntity.documentPath}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.franchiseDocument.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{franchiseDocumentEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.franchiseDocument.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseDocumentEntity.createdTime ? (
              <TextFormat value={franchiseDocumentEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.franchiseDocument.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{franchiseDocumentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.franchiseDocument.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseDocumentEntity.updatedTime ? (
              <TextFormat value={franchiseDocumentEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.franchiseDocument.franchise">Franchise</Translate>
          </dt>
          <dd>{franchiseDocumentEntity.franchise ? franchiseDocumentEntity.franchise.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/franchise-document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/franchise-document/${franchiseDocumentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FranchiseDocumentDetail;
