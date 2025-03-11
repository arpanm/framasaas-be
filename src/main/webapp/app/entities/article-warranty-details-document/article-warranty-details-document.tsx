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

import { getEntities, reset } from './article-warranty-details-document.reducer';

export const ArticleWarrantyDetailsDocument = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const articleWarrantyDetailsDocumentList = useAppSelector(state => state.articleWarrantyDetailsDocument.entities);
  const loading = useAppSelector(state => state.articleWarrantyDetailsDocument.loading);
  const links = useAppSelector(state => state.articleWarrantyDetailsDocument.links);
  const updateSuccess = useAppSelector(state => state.articleWarrantyDetailsDocument.updateSuccess);

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
      <h2 id="article-warranty-details-document-heading" data-cy="ArticleWarrantyDetailsDocumentHeading">
        <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.home.title">Article Warranty Details Documents</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/article-warranty-details-document/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.home.createLabel">
              Create new Article Warranty Details Document
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={articleWarrantyDetailsDocumentList ? articleWarrantyDetailsDocumentList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {articleWarrantyDetailsDocumentList && articleWarrantyDetailsDocumentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('documentPath')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.documentPath">Document Path</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentPath')} />
                  </th>
                  <th className="hand" onClick={sort('isValidated')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.isValidated">Is Validated</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isValidated')} />
                  </th>
                  <th className="hand" onClick={sort('validatedBy')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.validatedBy">Validated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('validatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('validatedTime')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.validatedTime">Validated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('validatedTime')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.articleWarranty">Article Warranty</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {articleWarrantyDetailsDocumentList.map((articleWarrantyDetailsDocument, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button
                        tag={Link}
                        to={`/article-warranty-details-document/${articleWarrantyDetailsDocument.id}`}
                        color="link"
                        size="sm"
                      >
                        {articleWarrantyDetailsDocument.id}
                      </Button>
                    </td>
                    <td>{articleWarrantyDetailsDocument.documentPath}</td>
                    <td>{articleWarrantyDetailsDocument.isValidated ? 'true' : 'false'}</td>
                    <td>{articleWarrantyDetailsDocument.validatedBy}</td>
                    <td>
                      {articleWarrantyDetailsDocument.validatedTime ? (
                        <TextFormat type="date" value={articleWarrantyDetailsDocument.validatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{articleWarrantyDetailsDocument.createddBy}</td>
                    <td>
                      {articleWarrantyDetailsDocument.createdTime ? (
                        <TextFormat type="date" value={articleWarrantyDetailsDocument.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{articleWarrantyDetailsDocument.updatedBy}</td>
                    <td>
                      {articleWarrantyDetailsDocument.updatedTime ? (
                        <TextFormat type="date" value={articleWarrantyDetailsDocument.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {articleWarrantyDetailsDocument.articleWarranty ? (
                        <Link to={`/article-warranty-details/${articleWarrantyDetailsDocument.articleWarranty.id}`}>
                          {articleWarrantyDetailsDocument.articleWarranty.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/article-warranty-details-document/${articleWarrantyDetailsDocument.id}`}
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
                          to={`/article-warranty-details-document/${articleWarrantyDetailsDocument.id}/edit`}
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
                          onClick={() =>
                            (window.location.href = `/article-warranty-details-document/${articleWarrantyDetailsDocument.id}/delete`)
                          }
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
                <Translate contentKey="framasaasApp.articleWarrantyDetailsDocument.home.notFound">
                  No Article Warranty Details Documents found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default ArticleWarrantyDetailsDocument;
