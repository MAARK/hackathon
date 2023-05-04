import React, { useEffect, useRef } from "react";
import mapboxgl from "!mapbox-gl"; // eslint-disable-line import/no-webpack-loader-syntax

mapboxgl.accessToken =
  "pk.eyJ1IjoiYmlrZXVzYWxhbmQiLCJhIjoiY2xnemV1aWJ2MDUwNjNkcHFuaWZrY2tjYiJ9.GoUKVkUM7k8oZlQ0C_6C0A";

const Map = () => {
  const mapContainer = useRef(null);
  const map = useRef(null);

  useEffect(() => {
    if (map.current) return; // initialize map only once
    map.current = new mapboxgl.Map({
      container: mapContainer.current,
      style: "mapbox://styles/mapbox/streets-v12",
      center: [-85.730827, 30.185181],
      zoom: 1.8,
    });

    map.current.on('load', () => {
      map.current.addSource('places', {
        'type': 'geojson',
        'data': {
          'type': 'FeatureCollection',
          'features': [
            {
              'type': 'Feature',
              'properties': {
                'description':
                    '<strong>Headquarters</strong><p>1835 Broadway St HQ Charlottesville, VA</p>'
              },
              'geometry': {
                'type': 'Point',
                'coordinates': [-78.4784941, 38.0300016],
              }
            },
            {
              'type': 'Feature',
              'properties': {
                'description':
                    '<strong>Durham</strong><p>800 Taylor St STE 300, Durham NC</p>'
              },
              'geometry': {
                'type': 'Point',
                'coordinates': [-78.901717, 35.996455]
              }
            },
            {
              'type': 'Feature',
              'properties': {
                'description':
                    '<strong>Columbus</strong><p>274 Marconi Blvd #300, Columbus, OH</p>'
              },
              'geometry': {
                'type': 'Point',
                'coordinates': [-83.001186, 39.969477]
              }
            },
            {
              'type': 'Feature',
              'properties': {
                'description':
                    '<strong>Vancouver</strong><p>401 W Georgia St #400, Vancouver BC</p>'
              },
              'geometry': {
                'type': 'Point',
                'coordinates': [-123.114825, 49.2812082]
              }
            },
            {
              'type': 'Feature',
              'properties': {
                'description':
                    '<strong>Poatek</strong><p>Porto Alegre, RS Brazil R. Dom Pedro II 978, 10th floor</p>'
              },
              'geometry': {
                'type': 'Point',
                'coordinates': [-51.1863404, -30.0154632]
              }
            },
            {
              'type': 'Feature',
              'properties': {
                'description':
                    '<strong>Sao Paulo</strong><p>Av Angelica 2529, 8th floor, Sao Paulo, Brazil</p>'
              },
              'geometry': {
                'type': 'Point',
                'coordinates': [-49, -22]
              }
            },
            {
              'type': 'Feature',
              'properties': {
                'description':
                    '<strong>Maark</strong><img src="https://avatars.githubusercontent.com/u/2652258?s=200&v=4" width="40" height="50"> <p>40 Cambridge Street, Boston MA</p>'
              },
              'geometry': {
                'type': 'Point',
                'coordinates': [-71.0740032, 42.3838378]
              }
            }
          ]
        }
      });
      // Add a layer showing the places.
      map.current.addLayer({
        'id': 'places',
        'type': 'circle',
        'source': 'places',
        'paint': {
          'circle-color': '#4264fb',
          'circle-radius': 6,
          'circle-stroke-width': 2,
          'circle-stroke-color': '#ffffff'
        }
      });

      // Create a popup, but don't add it to the map yet.
      const popup = new mapboxgl.Popup({
        closeButton: false,
        closeOnClick: false
      });

      map.current.on('mouseenter', 'places', (e) => {
        // Change the cursor style as a UI indicator.
        map.current.getCanvas().style.cursor = 'pointer';

        // Copy coordinates array.
        const coordinates = e.features[0].geometry.coordinates.slice();
        const description = e.features[0].properties.description;

        // Ensure that if the map is zoomed out such that multiple
        // copies of the feature are visible, the popup appears
        // over the copy being pointed to.
        while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
          coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
        }

        // Populate the popup and set its coordinates
        // based on the feature found.
        popup.setLngLat(coordinates).setHTML(description).addTo(map.current);
      });

      map.current.on('mouseleave', 'places', () => {
        map.current.getCanvas().style.cursor = '';
        popup.remove();
      });
    });
  });

  return (
    <div className="map">
      <h1 className="map__title">Map</h1>
      <div ref={mapContainer} className="map-container" />
    </div>
  );
};

export default Map;
