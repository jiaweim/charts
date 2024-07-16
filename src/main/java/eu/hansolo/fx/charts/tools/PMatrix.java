/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2016-2022 Gerrit Grunwald.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hansolo.fx.charts.tools;


/**
 * Projection Matrix
 * Used in CubeChart for Isometric projection
 */
public class PMatrix {
    private P2d xAxis;
    private P2d yAxis;
    private P2d zAxis;
    private P3d depth;
    private P2d origin;


    public PMatrix(final P2d xAxis, final P2d yAxis, final P2d zAxis, final P3d depth, final P2d origin) {
        this.xAxis  = xAxis;
        this.yAxis  = yAxis;
        this.zAxis  = zAxis;
        this.depth  = depth;
        this.origin = origin;
    }


    public P2d getxAxis() { return xAxis; }
    public void setxAxis(final P2d xAxis) { this.xAxis = xAxis; }

    public P2d getyAxis() { return yAxis; }
    public void setyAxis(final P2d yAxis) { this.yAxis = yAxis; }

    public P2d getzAxis() { return zAxis; }
    public void setzAxis(final P2d zAxis) { this.zAxis = zAxis; }

    public P3d getDepth() { return depth; }
    public void setDepth(final P3d depth) { this.depth = depth; }

    public P2d getOrigin() { return origin; }
    public void setOrigin(final P2d origin) { this.origin = origin; }

    public void setProjection(final PMatrix pMatrix) {
        this.xAxis = pMatrix.xAxis;
        this.yAxis = pMatrix.yAxis;
        this.zAxis = pMatrix.zAxis;
        if (null == pMatrix.depth) {
            this.depth = null == pMatrix.depth ? new P3d(this.xAxis.y, this.yAxis.y, -this.zAxis.y) : pMatrix.depth;
        }
    }

    public P3d project(final P3d p) {
        double x = p.x * this.xAxis.x + p.y * this.yAxis.x + p.z * this.zAxis.x + this.origin.x;
        double y = p.x * this.xAxis.y + p.y * this.yAxis.y + p.z * this.zAxis.y + this.origin.y;
        double z = p.x * this.depth.x + p.y * this.depth.y + p.z * this.depth.z;
        return new P3d(x, y, z);
    }
}
