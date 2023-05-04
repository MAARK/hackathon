import React from 'react';

const Behaviors = () => {
  const [behaviors, setBehaviors] = React.useState([]);

  React.useEffect(() => {
    fetch('/api/behaviors')
      .then(response => response.json())
      .then(data => setBehaviors(data));
  }, []);

  return (
    <div>
      <h1>Behaviors</h1>
      <ul>
        {behaviors.map(behavior => (
          <li key={behavior.id}>{behavior.name}</li>
        ))}
      </ul>
    </div>
  );
};

export default Behaviors;
