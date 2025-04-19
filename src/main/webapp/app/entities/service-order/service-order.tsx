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

import { getEntities, reset } from './service-order.reducer';

export const ServiceOrder = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const serviceOrderList = useAppSelector(state => state.serviceOrder.entities);
  const loading = useAppSelector(state => state.serviceOrder.loading);
  const links = useAppSelector(state => state.serviceOrder.links);
  const updateSuccess = useAppSelector(state => state.serviceOrder.updateSuccess);

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
      <h2 id="service-order-heading" data-cy="ServiceOrderHeading">
        <Translate contentKey="framasaasApp.serviceOrder.home.title">Service Orders</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.serviceOrder.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/service-order/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.serviceOrder.home.createLabel">Create new Service Order</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={serviceOrderList ? serviceOrderList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {serviceOrderList && serviceOrderList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.serviceOrder.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="framasaasApp.serviceOrder.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                  </th>
                  <th className="hand" onClick={sort('orderType')}>
                    <Translate contentKey="framasaasApp.serviceOrder.orderType">Order Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('orderType')} />
                  </th>
                  <th className="hand" onClick={sort('orderStatus')}>
                    <Translate contentKey="framasaasApp.serviceOrder.orderStatus">Order Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('orderStatus')} />
                  </th>
                  <th className="hand" onClick={sort('inWarranty')}>
                    <Translate contentKey="framasaasApp.serviceOrder.inWarranty">In Warranty</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('inWarranty')} />
                  </th>
                  <th className="hand" onClick={sort('isFree')}>
                    <Translate contentKey="framasaasApp.serviceOrder.isFree">Is Free</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isFree')} />
                  </th>
                  <th className="hand" onClick={sort('isSpareNeeded')}>
                    <Translate contentKey="framasaasApp.serviceOrder.isSpareNeeded">Is Spare Needed</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isSpareNeeded')} />
                  </th>
                  <th className="hand" onClick={sort('confirmedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrder.confirmedTime">Confirmed Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('confirmedTime')} />
                  </th>
                  <th className="hand" onClick={sort('closedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrder.closedTime">Closed Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('closedTime')} />
                  </th>
                  <th className="hand" onClick={sort('serviceCharge')}>
                    <Translate contentKey="framasaasApp.serviceOrder.serviceCharge">Service Charge</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('serviceCharge')} />
                  </th>
                  <th className="hand" onClick={sort('tax')}>
                    <Translate contentKey="framasaasApp.serviceOrder.tax">Tax</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('tax')} />
                  </th>
                  <th className="hand" onClick={sort('totalSpareCharges')}>
                    <Translate contentKey="framasaasApp.serviceOrder.totalSpareCharges">Total Spare Charges</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalSpareCharges')} />
                  </th>
                  <th className="hand" onClick={sort('totalSpareTax')}>
                    <Translate contentKey="framasaasApp.serviceOrder.totalSpareTax">Total Spare Tax</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalSpareTax')} />
                  </th>
                  <th className="hand" onClick={sort('totalCharges')}>
                    <Translate contentKey="framasaasApp.serviceOrder.totalCharges">Total Charges</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalCharges')} />
                  </th>
                  <th className="hand" onClick={sort('totalPaymentDone')}>
                    <Translate contentKey="framasaasApp.serviceOrder.totalPaymentDone">Total Payment Done</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalPaymentDone')} />
                  </th>
                  <th className="hand" onClick={sort('customerInvoicePath')}>
                    <Translate contentKey="framasaasApp.serviceOrder.customerInvoicePath">Customer Invoice Path</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('customerInvoicePath')} />
                  </th>
                  <th className="hand" onClick={sort('nps')}>
                    <Translate contentKey="framasaasApp.serviceOrder.nps">Nps</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('nps')} />
                  </th>
                  <th className="hand" onClick={sort('priority')}>
                    <Translate contentKey="framasaasApp.serviceOrder.priority">Priority</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('priority')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="framasaasApp.serviceOrder.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.serviceOrder.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.serviceOrder.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.serviceOrder.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrder.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.serviceOrder.customer">Customer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.serviceOrder.serviceMaster">Service Master</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.serviceOrder.article">Article</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="framasaasApp.serviceOrder.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {serviceOrderList.map((serviceOrder, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/service-order/${serviceOrder.id}`} color="link" size="sm">
                        {serviceOrder.id}
                      </Button>
                    </td>
                    <td>{serviceOrder.description}</td>
                    <td>
                      <Translate contentKey={`framasaasApp.ServiceOrderType.${serviceOrder.orderType}`} />
                    </td>
                    <td>
                      <Translate contentKey={`framasaasApp.ServiceOrderStatus.${serviceOrder.orderStatus}`} />
                    </td>
                    <td>{serviceOrder.inWarranty ? 'true' : 'false'}</td>
                    <td>{serviceOrder.isFree ? 'true' : 'false'}</td>
                    <td>{serviceOrder.isSpareNeeded ? 'true' : 'false'}</td>
                    <td>
                      {serviceOrder.confirmedTime ? (
                        <TextFormat type="date" value={serviceOrder.confirmedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrder.closedTime ? <TextFormat type="date" value={serviceOrder.closedTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{serviceOrder.serviceCharge}</td>
                    <td>{serviceOrder.tax}</td>
                    <td>{serviceOrder.totalSpareCharges}</td>
                    <td>{serviceOrder.totalSpareTax}</td>
                    <td>{serviceOrder.totalCharges}</td>
                    <td>{serviceOrder.totalPaymentDone}</td>
                    <td>{serviceOrder.customerInvoicePath}</td>
                    <td>{serviceOrder.nps}</td>
                    <td>{serviceOrder.priority}</td>
                    <td>{serviceOrder.isActive ? 'true' : 'false'}</td>
                    <td>{serviceOrder.createddBy}</td>
                    <td>
                      {serviceOrder.createdTime ? (
                        <TextFormat type="date" value={serviceOrder.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{serviceOrder.updatedBy}</td>
                    <td>
                      {serviceOrder.updatedTime ? (
                        <TextFormat type="date" value={serviceOrder.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrder.customer ? <Link to={`/customer/${serviceOrder.customer.id}`}>{serviceOrder.customer.id}</Link> : ''}
                    </td>
                    <td>
                      {serviceOrder.serviceMaster ? (
                        <Link to={`/service-order-master/${serviceOrder.serviceMaster.id}`}>{serviceOrder.serviceMaster.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{serviceOrder.article ? <Link to={`/article/${serviceOrder.article.id}`}>{serviceOrder.article.id}</Link> : ''}</td>
                    <td>{serviceOrder.address ? <Link to={`/address/${serviceOrder.address.id}`}>{serviceOrder.address.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/service-order/${serviceOrder.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/service-order/${serviceOrder.id}/edit`}
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
                          onClick={() => (window.location.href = `/service-order/${serviceOrder.id}/delete`)}
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
                <Translate contentKey="framasaasApp.serviceOrder.home.notFound">No Service Orders found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default ServiceOrder;
