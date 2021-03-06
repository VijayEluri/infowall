/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package infowall.domain.persistence.sql;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import infowall.domain.model.Dashboard;
import infowall.domain.persistence.DashboardRepository;

/**
 *
 */
public class InMemoryDashboardRepository implements DashboardRepository{

    private final Map<String,Dashboard> dashboards;

    public InMemoryDashboardRepository() {
        this.dashboards = Maps.newHashMap();
    }

    private void putDashboard(Dashboard dashboard) {
        dashboards.put(dashboard.getId(), dashboard);
    }

    @Override
    public void put(Dashboard entity) {
        putDashboard(entity);
    }

    @Override
    public boolean contains(String dashboardId) {
        return dashboards.containsKey(dashboardId);
    }

    @Override
    public Dashboard get(String id) {
        Dashboard dashboard = dashboards.get(id);
        // TODO
        if(dashboard == null){
            throw new NotFoundException();
        }
        return dashboard;
    }

    @Override
    public List<Dashboard> getAll() {
        return Lists.newArrayList(dashboards.values());
    }
}
