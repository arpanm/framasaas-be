import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './supporting-document.reducer';

export const SupportingDocument = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const supportingDocumentList = useAppSelector(state => state.supportingDocument.entities);
  const loading = useAppSelector(state => state.supportingDocument.loading);
  const links = useAppSelector(state => state.supportingDocument.links);
  const updateSuccess = useAppSelector(state => state.supportingDocument.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="supporting-document-heading" data-cy="SupportingDocumentHeading">
        <Translate contentKey="framasaasApp.supportingDocument.home.title">Supporting Documents</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.supportingDocument.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/supporting-document/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.supportingDocument.home.createLabel">Create new Supporting Document</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={supportingDocumentList ? supportingDocumentList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {supportingDocumentList && supportingDocumentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.supportingDocument.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('documentName')}>
                    <Translate contentKey="framasaasApp.supportingDocument.documentName">Document Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentName')} />
                  </th>
                  <th className="hand" onClick={sort('documentType')}>
                    <Translate contentKey="framasaasApp.supportingDocument.documentType">Document Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentType')} />
                  </th>
                  <th className="hand" onClick={sort('documentFormat')}>
                    <Translate contentKey="framasaasApp.supportingDocument.documentFormat">Document Format</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentFormat')} />
                  </th>
                  <th className="hand" onClick={sort('documentSize')}>
                    <Translate contentKey="framasaasApp.supportingDocument.documentSize">Document Size</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentSize')} />
                  </th>
                  <th className="hand" onClick={sort('documentPath')}>
                    <Translate contentKey="framasaasApp.supportingDocument.documentPath">Document Path</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentPath')} />
                  </th>
                  <th className="hand" onClick={sort('isValidated')}>
                    <Translate contentKey="framasaasApp.supportingDocument.isValidated">Is Validated</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isValidated')} />
                  </th>
                  <th className="hand" onClick={sort('validatedBy')}>
                    <Translate contentKey="framasaasApp.supportingDocument.validatedBy">Validated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('validatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('validatedTime')}>
                    <Translate contentKey="framasaasApp.supportingDocument.validatedTime">Validated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('validatedTime')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.supportingDocument.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.supportingDocument.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.supportingDocument.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.supportingDocument.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.supportingDocument.franchise">Franchise</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.supportingDocument.article">Article</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.supportingDocument.articleWarranty">Article Warranty</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.supportingDocument.serviceOrder">Service Order</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {supportingDocumentList.map((supportingDocument, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/supporting-document/${supportingDocument.id}`} color="link" size="sm">
                        {supportingDocument.id}
                      </Button>
                    </td>
                    <td>{supportingDocument.documentName}</td>
                    <td>
                      <Translate contentKey={`framasaasApp.DocumentType.${supportingDocument.documentType}`} />
                    </td>
                    <td>
                      <Translate contentKey={`framasaasApp.DocumentFormat.${supportingDocument.documentFormat}`} />
                    </td>
                    <td>{supportingDocument.documentSize}</td>
                    <td>{supportingDocument.documentPath}</td>
                    <td>{supportingDocument.isValidated ? 'true' : 'false'}</td>
                    <td>{supportingDocument.validatedBy}</td>
                    <td>
                      {supportingDocument.validatedTime ? (
                        <TextFormat type="date" value={supportingDocument.validatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{supportingDocument.createddBy}</td>
                    <td>
                      {supportingDocument.createdTime ? (
                        <TextFormat type="date" value={supportingDocument.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{supportingDocument.updatedBy}</td>
                    <td>
                      {supportingDocument.updatedTime ? (
                        <TextFormat type="date" value={supportingDocument.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {supportingDocument.franchise ? (
                        <Link to={`/franchise/${supportingDocument.franchise.id}`}>{supportingDocument.franchise.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {supportingDocument.article ? (
                        <Link to={`/article/${supportingDocument.article.id}`}>{supportingDocument.article.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {supportingDocument.articleWarranty ? (
                        <Link to={`/article-warranty-details/${supportingDocument.articleWarranty.id}`}>
                          {supportingDocument.articleWarranty.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {supportingDocument.serviceOrder ? (
                        <Link to={`/service-order/${supportingDocument.serviceOrder.id}`}>{supportingDocument.serviceOrder.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/supporting-document/${supportingDocument.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/supporting-document/${supportingDocument.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/supporting-document/${supportingDocument.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="framasaasApp.supportingDocument.home.notFound">No Supporting Documents found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default SupportingDocument;
