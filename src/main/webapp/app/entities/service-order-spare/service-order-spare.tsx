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

import { getEntities, reset } from './service-order-spare.reducer';

export const ServiceOrderSpare = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const serviceOrderSpareList = useAppSelector(state => state.serviceOrderSpare.entities);
  const loading = useAppSelector(state => state.serviceOrderSpare.loading);
  const links = useAppSelector(state => state.serviceOrderSpare.links);
  const updateSuccess = useAppSelector(state => state.serviceOrderSpare.updateSuccess);

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
      <h2 id="service-order-spare-heading" data-cy="ServiceOrderSpareHeading">
        <Translate contentKey="framasaasApp.serviceOrderSpare.home.title">Service Order Spares</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.serviceOrderSpare.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/service-order-spare/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.serviceOrderSpare.home.createLabel">Create new Service Order Spare</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={serviceOrderSpareList ? serviceOrderSpareList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {serviceOrderSpareList && serviceOrderSpareList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.serviceOrderSpare.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('price')}>
                    <Translate contentKey="framasaasApp.serviceOrderSpare.price">Price</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('price')} />
                  </th>
                  <th className="hand" onClick={sort('tax')}>
                    <Translate contentKey="framasaasApp.serviceOrderSpare.tax">Tax</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('tax')} />
                  </th>
                  <th className="hand" onClick={sort('totalCharge')}>
                    <Translate contentKey="framasaasApp.serviceOrderSpare.totalCharge">Total Charge</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalCharge')} />
                  </th>
                  <th className="hand" onClick={sort('orderedFrom')}>
                    <Translate contentKey="framasaasApp.serviceOrderSpare.orderedFrom">Ordered From</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('orderedFrom')} />
                  </th>
                  <th className="hand" onClick={sort('spareStatus')}>
                    <Translate contentKey="framasaasApp.serviceOrderSpare.spareStatus">Spare Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('spareStatus')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderSpare.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderSpare.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderSpare.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderSpare.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.serviceOrderSpare.serviceOrder">Service Order</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.serviceOrderSpare.product">Product</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {serviceOrderSpareList.map((serviceOrderSpare, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/service-order-spare/${serviceOrderSpare.id}`} color="link" size="sm">
                        {serviceOrderSpare.id}
                      </Button>
                    </td>
                    <td>{serviceOrderSpare.price}</td>
                    <td>{serviceOrderSpare.tax}</td>
                    <td>{serviceOrderSpare.totalCharge}</td>
                    <td>
                      <Translate contentKey={`framasaasApp.SpareOrderedFrom.${serviceOrderSpare.orderedFrom}`} />
                    </td>
                    <td>
                      <Translate contentKey={`framasaasApp.ServiceOrderSpareStatus.${serviceOrderSpare.spareStatus}`} />
                    </td>
                    <td>{serviceOrderSpare.createddBy}</td>
                    <td>
                      {serviceOrderSpare.createdTime ? (
                        <TextFormat type="date" value={serviceOrderSpare.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{serviceOrderSpare.updatedBy}</td>
                    <td>
                      {serviceOrderSpare.updatedTime ? (
                        <TextFormat type="date" value={serviceOrderSpare.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderSpare.serviceOrder ? (
                        <Link to={`/service-order/${serviceOrderSpare.serviceOrder.id}`}>{serviceOrderSpare.serviceOrder.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {serviceOrderSpare.product ? (
                        <Link to={`/product/${serviceOrderSpare.product.id}`}>{serviceOrderSpare.product.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/service-order-spare/${serviceOrderSpare.id}`}
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
                          to={`/service-order-spare/${serviceOrderSpare.id}/edit`}
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
                          onClick={() => (window.location.href = `/service-order-spare/${serviceOrderSpare.id}/delete`)}
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
                <Translate contentKey="framasaasApp.serviceOrderSpare.home.notFound">No Service Order Spares found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default ServiceOrderSpare;
