import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './article-warranty-details.reducer';

export const ArticleWarrantyDetails = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const articleWarrantyDetailsList = useAppSelector(state => state.articleWarrantyDetails.entities);
  const loading = useAppSelector(state => state.articleWarrantyDetails.loading);
  const links = useAppSelector(state => state.articleWarrantyDetails.links);
  const updateSuccess = useAppSelector(state => state.articleWarrantyDetails.updateSuccess);

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
      <h2 id="article-warranty-details-heading" data-cy="ArticleWarrantyDetailsHeading">
        <Translate contentKey="framasaasApp.articleWarrantyDetails.home.title">Article Warranty Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.articleWarrantyDetails.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/article-warranty-details/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.articleWarrantyDetails.home.createLabel">Create new Article Warranty Details</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={articleWarrantyDetailsList ? articleWarrantyDetailsList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {articleWarrantyDetailsList && articleWarrantyDetailsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('warrantyType')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.warrantyType">Warranty Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('warrantyType')} />
                  </th>
                  <th className="hand" onClick={sort('vendorArticleWarrantyId')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.vendorArticleWarrantyId">
                      Vendor Article Warranty Id
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('vendorArticleWarrantyId')} />
                  </th>
                  <th className="hand" onClick={sort('vendorWarrantyMasterId')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.vendorWarrantyMasterId">Vendor Warranty Master Id</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('vendorWarrantyMasterId')} />
                  </th>
                  <th className="hand" onClick={sort('startDate')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.startDate">Start Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('startDate')} />
                  </th>
                  <th className="hand" onClick={sort('endDate')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.endDate">End Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('endDate')} />
                  </th>
                  <th className="hand" onClick={sort('soldBy')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.soldBy">Sold By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('soldBy')} />
                  </th>
                  <th className="hand" onClick={sort('soldByUser')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.soldByUser">Sold By User</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('soldByUser')} />
                  </th>
                  <th className="hand" onClick={sort('soldDate')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.soldDate">Sold Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('soldDate')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.article">Article</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.articleWarrantyDetails.warrantyMaster">Warranty Master</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {articleWarrantyDetailsList.map((articleWarrantyDetails, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/article-warranty-details/${articleWarrantyDetails.id}`} color="link" size="sm">
                        {articleWarrantyDetails.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`framasaasApp.WarrantyType.${articleWarrantyDetails.warrantyType}`} />
                    </td>
                    <td>{articleWarrantyDetails.vendorArticleWarrantyId}</td>
                    <td>{articleWarrantyDetails.vendorWarrantyMasterId}</td>
                    <td>
                      {articleWarrantyDetails.startDate ? (
                        <TextFormat type="date" value={articleWarrantyDetails.startDate} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {articleWarrantyDetails.endDate ? (
                        <TextFormat type="date" value={articleWarrantyDetails.endDate} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      <Translate contentKey={`framasaasApp.SoldBy.${articleWarrantyDetails.soldBy}`} />
                    </td>
                    <td>{articleWarrantyDetails.soldByUser}</td>
                    <td>
                      {articleWarrantyDetails.soldDate ? (
                        <TextFormat type="date" value={articleWarrantyDetails.soldDate} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{articleWarrantyDetails.isActive ? 'true' : 'false'}</td>
                    <td>{articleWarrantyDetails.createddBy}</td>
                    <td>
                      {articleWarrantyDetails.createdTime ? (
                        <TextFormat type="date" value={articleWarrantyDetails.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{articleWarrantyDetails.updatedBy}</td>
                    <td>
                      {articleWarrantyDetails.updatedTime ? (
                        <TextFormat type="date" value={articleWarrantyDetails.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {articleWarrantyDetails.article ? (
                        <Link to={`/article/${articleWarrantyDetails.article.id}`}>{articleWarrantyDetails.article.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {articleWarrantyDetails.warrantyMaster ? (
                        <Link to={`/warranty-master/${articleWarrantyDetails.warrantyMaster.id}`}>
                          {articleWarrantyDetails.warrantyMaster.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/article-warranty-details/${articleWarrantyDetails.id}`}
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
                          to={`/article-warranty-details/${articleWarrantyDetails.id}/edit`}
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
                          onClick={() => (window.location.href = `/article-warranty-details/${articleWarrantyDetails.id}/delete`)}
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
                <Translate contentKey="framasaasApp.articleWarrantyDetails.home.notFound">No Article Warranty Details found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default ArticleWarrantyDetails;
