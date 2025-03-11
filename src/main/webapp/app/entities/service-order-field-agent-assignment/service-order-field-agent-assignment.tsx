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

import { getEntities, reset } from './service-order-field-agent-assignment.reducer';

export const ServiceOrderFieldAgentAssignment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const serviceOrderFieldAgentAssignmentList = useAppSelector(state => state.serviceOrderFieldAgentAssignment.entities);
  const loading = useAppSelector(state => state.serviceOrderFieldAgentAssignment.loading);
  const links = useAppSelector(state => state.serviceOrderFieldAgentAssignment.links);
  const updateSuccess = useAppSelector(state => state.serviceOrderFieldAgentAssignment.updateSuccess);

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
      <h2 id="service-order-field-agent-assignment-heading" data-cy="ServiceOrderFieldAgentAssignmentHeading">
        <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.home.title">Service Order Field Agent Assignments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/service-order-field-agent-assignment/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.home.createLabel">
              Create new Service Order Field Agent Assignment
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={serviceOrderFieldAgentAssignmentList ? serviceOrderFieldAgentAssignmentList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {serviceOrderFieldAgentAssignmentList && serviceOrderFieldAgentAssignmentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('serviceOrderAssignmentStatus')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.serviceOrderAssignmentStatus">
                      Service Order Assignment Status
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('serviceOrderAssignmentStatus')} />
                  </th>
                  <th className="hand" onClick={sort('nps')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.nps">Nps</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('nps')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('assignedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.assignedTime">Assigned Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('assignedTime')} />
                  </th>
                  <th className="hand" onClick={sort('movedBackTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.movedBackTime">Moved Back Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('movedBackTime')} />
                  </th>
                  <th className="hand" onClick={sort('visitTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.visitTime">Visit Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('visitTime')} />
                  </th>
                  <th className="hand" onClick={sort('spareOrderTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.spareOrderTime">Spare Order Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('spareOrderTime')} />
                  </th>
                  <th className="hand" onClick={sort('spareDeliveryTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.spareDeliveryTime">Spare Delivery Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('spareDeliveryTime')} />
                  </th>
                  <th className="hand" onClick={sort('completedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.completedTime">Completed Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('completedTime')} />
                  </th>
                  <th className="hand" onClick={sort('completionOTP')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.completionOTP">Completion OTP</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('completionOTP')} />
                  </th>
                  <th className="hand" onClick={sort('cancellationOTP')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.cancellationOTP">Cancellation OTP</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('cancellationOTP')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {serviceOrderFieldAgentAssignmentList.map((serviceOrderFieldAgentAssignment, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button
                        tag={Link}
                        to={`/service-order-field-agent-assignment/${serviceOrderFieldAgentAssignment.id}`}
                        color="link"
                        size="sm"
                      >
                        {serviceOrderFieldAgentAssignment.id}
                      </Button>
                    </td>
                    <td>
                      <Translate
                        contentKey={`framasaasApp.ServiceOrderAssignmentStatus.${serviceOrderFieldAgentAssignment.serviceOrderAssignmentStatus}`}
                      />
                    </td>
                    <td>{serviceOrderFieldAgentAssignment.nps}</td>
                    <td>{serviceOrderFieldAgentAssignment.isActive ? 'true' : 'false'}</td>
                    <td>
                      {serviceOrderFieldAgentAssignment.assignedTime ? (
                        <TextFormat type="date" value={serviceOrderFieldAgentAssignment.assignedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderFieldAgentAssignment.movedBackTime ? (
                        <TextFormat type="date" value={serviceOrderFieldAgentAssignment.movedBackTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderFieldAgentAssignment.visitTime ? (
                        <TextFormat type="date" value={serviceOrderFieldAgentAssignment.visitTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderFieldAgentAssignment.spareOrderTime ? (
                        <TextFormat type="date" value={serviceOrderFieldAgentAssignment.spareOrderTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderFieldAgentAssignment.spareDeliveryTime ? (
                        <TextFormat type="date" value={serviceOrderFieldAgentAssignment.spareDeliveryTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {serviceOrderFieldAgentAssignment.completedTime ? (
                        <TextFormat type="date" value={serviceOrderFieldAgentAssignment.completedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{serviceOrderFieldAgentAssignment.completionOTP}</td>
                    <td>{serviceOrderFieldAgentAssignment.cancellationOTP}</td>
                    <td>{serviceOrderFieldAgentAssignment.createddBy}</td>
                    <td>
                      {serviceOrderFieldAgentAssignment.createdTime ? (
                        <TextFormat type="date" value={serviceOrderFieldAgentAssignment.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{serviceOrderFieldAgentAssignment.updatedBy}</td>
                    <td>
                      {serviceOrderFieldAgentAssignment.updatedTime ? (
                        <TextFormat type="date" value={serviceOrderFieldAgentAssignment.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/service-order-field-agent-assignment/${serviceOrderFieldAgentAssignment.id}`}
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
                          to={`/service-order-field-agent-assignment/${serviceOrderFieldAgentAssignment.id}/edit`}
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
                            (window.location.href = `/service-order-field-agent-assignment/${serviceOrderFieldAgentAssignment.id}/delete`)
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
                <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.home.notFound">
                  No Service Order Field Agent Assignments found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default ServiceOrderFieldAgentAssignment;
