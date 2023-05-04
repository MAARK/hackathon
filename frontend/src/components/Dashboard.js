import React, { useState } from "react";
import axios from "axios";

const Dashboard = () => {
  const [query, setQuery] = useState("");
  const [searchResults, setResults] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (query) => {
    setIsLoading(true);
    try {
      const response = await axios.post("http://localhost:8080/search", {
        query,
      });
      const data = response.data;
      setIsLoading(false);
      if (!data) {
        throw new Error("Response was empty");
      }
      return data;
    } catch (error) {
      setIsLoading(false);
      console.error(error);
      throw new Error("Network response was not ok");
    }
  };

  const handleSearch = async (event) => {
    event.preventDefault();
    const data = await handleSubmit(query);
    setResults(data);
  };

  const dummyData = [
    {
      name: "Jacqueline Rowe",
      description:
        "Left work before 12 PM today and missed 75% of meetings this week.",
    },
    {
      name: "Bradford Blackwell",
      description:
        "Left work after 12 PM today and did not miss any meetings this week.",
    },
    {
      name: "Leona Obrien",
      description:
        "Left work after 12 PM today and missed 10% of meetings this week.",
    },
    {
      name: "Lilah Holt",
      description:
        "Left work after 12 PM today and missed 75% of meetings this week.",
    },
    {
      name: "Gia Hall",
      description:
        "Left work before 12 PM today and missed 10% of meetings this week.",
    },
    {
      name: "Harvey Reeves",
      description:
        "Left work before 12 PM today and missed 75% of meetings this week.",
    },
    {
      name: "Beau Mayo",
      description:
        "Left work after 12 PM today and missed 75% of meetings this week.",
    },
    {
      name: "Emily Nguyen",
      description:
        "Left work after 12 PM today and missed 75% of meetings this week.",
    },
    {
      name: "Olivia Lin",
      description:
        "Left work before 12 PM today and missed 75% of meetings this week.",
    },
    {
      name: "Sophia Wu",
      description:
        "Left work after 12 PM today and missed 75% of meetings this week.",
    },
    {
      name: "Isabella Kim",
      description:
        "Left work after 12 PM today and missed 75% of meetings this week.",
    },
    {
      name: "Joshua Tran",
      description:
        "Left work before 12 PM today and missed 75% of meetings this week.",
    },
    {
      name: "William Kim",
      description:
        "Left work after 12 PM today and missed 10% of meetings this week.",
    },
    {
      name: "Justin Kim",
      description:
        "Left work after 12 PM today and missed 75% of meetings this week.",
    },
  ];

  const handleChange = (event) => {
    setQuery(event.target.value);
  };

  return (
    <div>
      <h2>Search</h2>
      <form onSubmit={handleSearch} className="search-form">
        <label>
          <input
            className="search-bar"
            type="text"
            placeholder="Search for any behavior..."
            value={query}
            onChange={handleChange}
          />
        </label>
        <button className="search-button" type="submit">Search</button>
      </form>
      {isLoading && <p>Loading...</p>}
      {searchResults.length > 0 && (
        <ul style={{ listStyle: "none", padding: 0 }}>
          {searchResults.map((item) => (
            <li key={item.name} className="search-list-item">
              <strong>{item.name}:</strong>
              <span>{item.description}</span>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Dashboard;
