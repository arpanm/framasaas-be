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

import { getEntities, reset } from './franchise-document.reducer';

export const FranchiseDocument = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const franchiseDocumentList = useAppSelector(state => state.franchiseDocument.entities);
  const loading = useAppSelector(state => state.franchiseDocument.loading);
  const links = useAppSelector(state => state.franchiseDocument.links);
  const updateSuccess = useAppSelector(state => state.franchiseDocument.updateSuccess);

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
      <h2 id="franchise-document-heading" data-cy="FranchiseDocumentHeading">
        <Translate contentKey="framasaasApp.franchiseDocument.home.title">Franchise Documents</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.franchiseDocument.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/franchise-document/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.franchiseDocument.home.createLabel">Create new Franchise Document</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={franchiseDocumentList ? franchiseDocumentList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {franchiseDocumentList && franchiseDocumentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('documentName')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.documentName">Document Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentName')} />
                  </th>
                  <th className="hand" onClick={sort('documentType')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.documentType">Document Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentType')} />
                  </th>
                  <th className="hand" onClick={sort('documentFormat')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.documentFormat">Document Format</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentFormat')} />
                  </th>
                  <th className="hand" onClick={sort('documentSize')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.documentSize">Document Size</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentSize')} />
                  </th>
                  <th className="hand" onClick={sort('documentPath')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.documentPath">Document Path</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentPath')} />
                  </th>
                  <th className="hand" onClick={sort('isValidated')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.isValidated">Is Validated</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isValidated')} />
                  </th>
                  <th className="hand" onClick={sort('validatedBy')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.validatedBy">Validated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('validatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('validatedTime')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.validatedTime">Validated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('validatedTime')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.franchiseDocument.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.franchiseDocument.franchise">Franchise</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {franchiseDocumentList.map((franchiseDocument, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/franchise-document/${franchiseDocument.id}`} color="link" size="sm">
                        {franchiseDocument.id}
                      </Button>
                    </td>
                    <td>{franchiseDocument.documentName}</td>
                    <td>
                      <Translate contentKey={`framasaasApp.DocumentType.${franchiseDocument.documentType}`} />
                    </td>
                    <td>
                      <Translate contentKey={`framasaasApp.DocumentFormat.${franchiseDocument.documentFormat}`} />
                    </td>
                    <td>{franchiseDocument.documentSize}</td>
                    <td>{franchiseDocument.documentPath}</td>
                    <td>{franchiseDocument.isValidated ? 'true' : 'false'}</td>
                    <td>{franchiseDocument.validatedBy}</td>
                    <td>
                      {franchiseDocument.validatedTime ? (
                        <TextFormat type="date" value={franchiseDocument.validatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{franchiseDocument.createddBy}</td>
                    <td>
                      {franchiseDocument.createdTime ? (
                        <TextFormat type="date" value={franchiseDocument.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{franchiseDocument.updatedBy}</td>
                    <td>
                      {franchiseDocument.updatedTime ? (
                        <TextFormat type="date" value={franchiseDocument.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {franchiseDocument.franchise ? (
                        <Link to={`/franchise/${franchiseDocument.franchise.id}`}>{franchiseDocument.franchise.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/franchise-document/${franchiseDocument.id}`}
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
                          to={`/franchise-document/${franchiseDocument.id}/edit`}
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
                          onClick={() => (window.location.href = `/franchise-document/${franchiseDocument.id}/delete`)}
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
                <Translate contentKey="framasaasApp.franchiseDocument.home.notFound">No Franchise Documents found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default FranchiseDocument;
